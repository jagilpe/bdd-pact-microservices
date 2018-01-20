package com.gilpereda.bddpactmicroservices.apigateway;

import au.com.dius.pact.model.*;
import au.com.dius.pact.provider.ConsumerInfo;
import au.com.dius.pact.provider.ProviderInfo;
import au.com.dius.pact.provider.ProviderVerifier;
import au.com.dius.pact.provider.junit.target.BaseTarget;
import com.gilpereda.bddpactmicroservices.apigateway.exception.ProviderNameNotDefinedException;
import org.assertj.core.util.Maps;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;

import java.util.*;
import java.util.stream.Collectors;

class ApiGatewayTarget extends BaseTarget implements PactsWriter {

    private final RouteLocator routeLocator;
    private final ApiGatewayPactMappingConfiguration configuration;
    private final Map<String, RequestResponsePact> outputPacts = new HashMap<>();

    ApiGatewayTarget(final RouteLocator routeLocator, final ApiGatewayPactMappingConfiguration configuration) {
        this.routeLocator = routeLocator;
        this.configuration = configuration;
    }

    @Override
    public void testInteraction(final String consumerName, final Interaction interaction, final PactSource source) {
        ProviderInfo providerInfo = getProviderInfo(source);
        ConsumerInfo consumerInfo = new ConsumerInfo(consumerName);
        ProviderVerifier verifier = setupVerifier(interaction, providerInfo, consumerInfo);

        Map<String, Object> failures = new HashMap<>();
        if (interaction instanceof RequestResponseInteraction) {
            failures.putAll(verifyRequestMapping((RequestResponseInteraction) interaction, source));
            reportTestResult(failures.isEmpty(), verifier);
        }

        try {
            if (!failures.isEmpty()) {
                verifier.displayFailures(failures);
                throw getAssertionError(failures);
            }
        } finally {
            verifier.finialiseReports();
        }
    }

    @Override
    protected ProviderInfo getProviderInfo(final PactSource source) {
        return new ProviderInfo(testClass.getAnnotation(au.com.dius.pact.provider.junit.Provider.class).value());
    }

    @Override
    protected ProviderVerifier setupVerifier(final Interaction interaction, final ProviderInfo provider, final ConsumerInfo consumer) {
        ProviderVerifier verifier = new ProviderVerifier();

        setupReporters(verifier, provider.getName(), interaction.getDescription());

        verifier.initialiseReporters(provider);
        verifier.reportVerificationForConsumer(consumer, provider);

        interaction.getProviderStates().forEach(
            providerState -> verifier.reportStateForInteraction(providerState.getName(), provider, consumer, true));

        verifier.reportInteractionDescription(interaction);
        return verifier;
    }

    @Override
    public void writePacts(final String pactDirectory, final PactSpecVersion pactSpecVersion) {
        outputPacts.forEach((routeId, pact) -> pact.write(pactDirectory, pactSpecVersion));
    }

    private Map<String,Boolean> verifyRequestMapping(final RequestResponseInteraction interaction, final PactSource source) {
        String requestPath = interaction.getRequest().getPath();
        Route matchingRoute = getMatchingRoute(requestPath);
        String error = null;
        if (matchingRoute != null) {
            try {
                addInteractionToProvidersPacts(interaction, source, matchingRoute);
            } catch (ProviderNameNotDefinedException e) {
                error = "No provider name for route " + matchingRoute.getPath();
            }
        } else {
            error = requestPath + " has no mapping to any service.";
        }
        return error != null ? Maps.newHashMap(error, false) : Collections.emptyMap();
    }

    private void addInteractionToProvidersPacts(
        final RequestResponseInteraction interaction,
        final PactSource source,
        final Route matchingRoute) throws ProviderNameNotDefinedException {
        String providerName = getProviderForRoute(matchingRoute).orElseThrow(ProviderNameNotDefinedException::new);
        RequestResponsePact pact = outputPacts.get(providerName);
        if (pact == null) {
            Provider provider = new Provider(providerName);
            Consumer consumer = new Consumer(getProviderInfo(source).getName());
            pact = new RequestResponsePact(provider, consumer, new ArrayList<>());
            outputPacts.put(providerName, pact);
        }
        pact.mergeInteractions(getNewInteraction(interaction, matchingRoute));
    }

    private Optional<String> getProviderForRoute(final Route route) {
        return configuration.getServicesUrls().entrySet().stream()
            .filter(entry -> entry.getValue().equals(route.getLocation()))
            .map(Map.Entry::getKey)
            .findFirst();
    }

    private Route getMatchingRoute(final String requestPath) {
        return Optional.ofNullable(routeLocator.getMatchingRoute(requestPath))
            .filter(route -> !configuration.getExcluded().contains(route.getId()))
            .orElse(null);
    }

    private List<Interaction> getNewInteraction(final RequestResponseInteraction interaction, final Route matchingRoute) {
        RequestResponseInteraction outInteraction = new RequestResponseInteraction();

        Request request = interaction.getRequest().copy();
        request.setPath(matchingRoute.getPath());
        List<ProviderState> providerStates = interaction.getProviderStates().stream()
            .map(providerState -> new ProviderState(providerState.getName(), providerState.getParams()))
            .collect(Collectors.toList());

        outInteraction.setRequest(request);
        outInteraction.setResponse(interaction.getResponse().copy());
        outInteraction.setDescription(interaction.getDescription());
        outInteraction.setProviderStates(providerStates);

        return Collections.singletonList(outInteraction);
    }
}
