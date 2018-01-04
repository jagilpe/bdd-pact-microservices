package com.gilpereda.bddpactmicroservices.apigateway;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.assertj.core.util.Maps;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;

import au.com.dius.pact.model.Consumer;
import au.com.dius.pact.model.Interaction;
import au.com.dius.pact.model.PactSource;
import au.com.dius.pact.model.PactSpecVersion;
import au.com.dius.pact.model.Provider;
import au.com.dius.pact.model.ProviderState;
import au.com.dius.pact.model.Request;
import au.com.dius.pact.model.RequestResponseInteraction;
import au.com.dius.pact.model.RequestResponsePact;
import au.com.dius.pact.provider.ConsumerInfo;
import au.com.dius.pact.provider.ProviderInfo;
import au.com.dius.pact.provider.ProviderVerifier;
import au.com.dius.pact.provider.junit.target.BaseTarget;

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
        if (matchingRoute != null) {
            addInteractionToProvidersPacts(interaction, source, matchingRoute);
            return Collections.emptyMap();
        } else {
            return Maps.newHashMap(requestPath + " has no mapping to any service: ", false);
        }
    }

    private void addInteractionToProvidersPacts(
        final RequestResponseInteraction interaction, final PactSource source, final Route matchingRoute) {
        String routeId = matchingRoute.getId();
        RequestResponsePact pact = outputPacts.get(routeId);
        if (pact == null) {
            Provider provider = new Provider(routeId);
            Consumer consumer = new Consumer(getProviderInfo(source).getName());
            pact = new RequestResponsePact(provider, consumer, new ArrayList<>());
            outputPacts.put(routeId, pact);
        }
        pact.mergeInteractions(getNewInteraction(interaction, matchingRoute));
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
