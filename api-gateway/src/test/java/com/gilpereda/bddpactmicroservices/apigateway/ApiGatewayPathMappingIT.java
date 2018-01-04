package com.gilpereda.bddpactmicroservices.apigateway;

import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import au.com.dius.pact.model.PactSpecVersion;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;

@RunWith(ApiGatewayPactRunner.class)
@Provider("api-gateway")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = { ApiGateway.class, ApiGatewayPathMappingIT.ApiGatewayITConfig.class }
)
@PactBroker(host = "localhost", port = "1080")
@ActiveProfiles("test")
public class ApiGatewayPathMappingIT {

    @TestTarget
    @Autowired
    public Target target;

    @After
    public void writePacts() {
        if (target instanceof PactsWriter) {
            ((PactsWriter) target).writePacts("build/pacts", PactSpecVersion.V3);
        }
    }

    @TestConfiguration
    public static class ApiGatewayITConfig {
        @Autowired
        private RouteLocator routeLocator;

        @Autowired
        private ApiGatewayPactMappingConfiguration configuration;

        @Bean
        public Target getTarget() {
            return new ApiGatewayTarget(routeLocator, configuration);
        }
    }
}
