package com.gilpereda.bddpactmicroservices.apigateway;

import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;
import org.springframework.test.context.TestContextManager;

import au.com.dius.pact.model.Interaction;
import au.com.dius.pact.model.Pact;
import au.com.dius.pact.model.PactSource;
import au.com.dius.pact.provider.spring.SpringInteractionRunner;

/**
 * Our goal is to check that all the interactions defined in the pact for the API Gateway are mapped
 * to a service, and generate a new pact for each of this provider services.
 *
 * We extend the SpringInteractionRunner because we need the autoconfiguration of the test context
 * that the SpringInteraction Runner gives, but the default InteractionRunner requires a method annotated
 * with State for each of the states defined in the pact.
 * We are not going to check any of the interactions of the pact, but to divide the original pact into
 * pacts that each of the mapped services have to satisfy, and therefore we don't need this methods.
 */
class ApiGatewayInteractionRunner extends SpringInteractionRunner {
    ApiGatewayInteractionRunner(final TestClass testClass, final Pact pact, final PactSource pactSource, final TestContextManager testContextManager) {
        super(testClass, pact, pactSource, testContextManager);
    }

    @Override
    protected Statement withStateChanges(final Interaction interaction, final Object target, final Statement statement) {
        return statement;
    }
}
