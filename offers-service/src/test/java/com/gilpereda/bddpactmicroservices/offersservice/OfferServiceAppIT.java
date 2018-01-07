package com.gilpereda.bddpactmicroservices.offersservice;

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
@Provider("offers-service")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = OfferServiceApp.class)
@PactBroker(protocol = "http", host = "127.0.0.1", port = "1080")
public class OfferServiceAppIT {

    @TestTarget
    public Target target = new HttpTarget(8082);

    @State("there are 8 offers for product 1")
    public void setUpStateThereAre8OffersForProduct1() {

    }
}
