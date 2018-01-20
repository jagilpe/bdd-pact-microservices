package com.gilpereda.bddpactmicroservices.apigateway;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "api-gateway.providers-mapping")
public final class ApiGatewayPactMappingConfiguration {

    private final List<String> excluded = new ArrayList<>();
    private final Map<String, String> servicesUrls = new HashMap<>();

    public List<String> getExcluded() {
        return excluded;
    }

    public Map<String, String> getServicesUrls() {
        return servicesUrls;
    }

}
