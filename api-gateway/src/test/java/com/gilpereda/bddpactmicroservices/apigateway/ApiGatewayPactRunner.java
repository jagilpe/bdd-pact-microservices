package com.gilpereda.bddpactmicroservices.apigateway;

import org.jetbrains.annotations.NotNull;
import org.junit.runners.model.TestClass;
import org.springframework.test.context.TestContextManager;

import au.com.dius.pact.model.Pact;
import au.com.dius.pact.model.PactSource;
import au.com.dius.pact.provider.junit.InteractionRunner;
import au.com.dius.pact.provider.junit.RestPactRunner;

public class ApiGatewayPactRunner extends RestPactRunner {
    private TestContextManager testContextManager;

    public ApiGatewayPactRunner(final Class<?> clazz) {
        super(clazz);
    }

    @NotNull
    @Override
    protected InteractionRunner newInteractionRunner(
        @NotNull final TestClass testClass, @NotNull final Pact pact, @NotNull final PactSource pactSource) {
        return new ApiGatewayInteractionRunner(testClass, pact, pactSource, initTestContextManager(testClass));
    }

    private TestContextManager initTestContextManager(final TestClass testClass) {
        if (testContextManager == null) {
            testContextManager = new TestContextManager(testClass.getJavaClass());
        }
        return testContextManager;
    }
}
