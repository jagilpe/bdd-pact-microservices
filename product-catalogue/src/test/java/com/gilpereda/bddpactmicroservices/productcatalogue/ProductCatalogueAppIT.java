package com.gilpereda.bddpactmicroservices.productcatalogue;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(SpringRestPactRunner.class)
@Provider("products-catalogue-service")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = ProductCatalogueApp.class)
@PactBroker(protocol = "http", host = "127.0.0.1", port = "1080")
public class ProductCatalogueAppIT {

    @TestTarget
    public Target target = new HttpTarget(8081);

    @State("there are 5 products in the category 1")
    public void setUpStateThereAreFiveProductsInCategory1() {

    }

    @State("there is a product iPhone 8")
    public void setUpStateThereIsAProductIPhone8() {

    }
}
