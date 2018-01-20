package com.gilpereda.bddpactmicroservices.apigateway;

import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductCatalogueMappingConfiguration {

    @Bean
    public PatternServiceRouteMapper serviceRouteMapper() {
        return new PatternServiceRouteMapper(
            "/api/v1/(?<path>[categories|products])",
            "/api/v1/product-catalogue/${path}");
    }
}
