package com.gilpereda.bddpactmicroservices.apigateway;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api-gateway.providers-mapping")
public final class ApiGatewayPactMappingConfiguration {

    private final List<String> excluded = new ArrayList<>();

    public List<String> getExcluded() {
        return excluded;
    }

}
