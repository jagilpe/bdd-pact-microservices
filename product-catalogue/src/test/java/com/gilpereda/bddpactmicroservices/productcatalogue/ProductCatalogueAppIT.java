package com.gilpereda.bddpactmicroservices.productcatalogue;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import com.gilpereda.bddpactmicroservices.productcatalogue.util.DataFixtureLoader;
import com.gilpereda.bddpactmicroservices.productcatalogue.util.TestCase;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(SpringRestPactRunner.class)
@Provider("products-catalogue-service")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = ProductCatalogueApp.class )
@PactBroker(protocol = "http", host = "127.0.0.1", port = "1080")
public class ProductCatalogueAppIT {

    @TestTarget
    public Target target = new HttpTarget(8081);

    @Autowired
    private DataFixtureLoader dataFixtureLoader;

    @After
    public void clearDatabase() throws Exception {
        dataFixtureLoader.reset();
    }

    @State("there are 5 products in the category 1")
    public void setUpStateThereAreFiveProductsInCategory1() throws Exception {
        dataFixtureLoader.setUpFixture(TestCase.FIVE_PRODUCTS_IN_CATEGORY_1);
    }

    @State("there is a product iPhone 8")
    public void setUpStateThereIsAProductIPhone8() throws Exception {
        dataFixtureLoader.setUpFixture(TestCase.ONE_PRODUCT);
    }
}
