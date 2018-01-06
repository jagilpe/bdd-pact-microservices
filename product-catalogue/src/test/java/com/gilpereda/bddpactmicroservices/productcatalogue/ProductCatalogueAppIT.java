package com.gilpereda.bddpactmicroservices.productcatalogue;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.datatype.DataType;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@RunWith(SpringRestPactRunner.class)
@Provider("products-catalogue-service")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    classes = { ProductCatalogueApp.class, ProductCatalogueAppIT.ProductCatalogueAppITConfiguration.class })
@PactBroker(protocol = "http", host = "127.0.0.1", port = "1080")
public class ProductCatalogueAppIT {

    @TestTarget
    public Target target = new HttpTarget(8081);

    @Autowired
    private IDatabaseTester databaseTester;

    @After
    public void clearDatabase() throws Exception {
        databaseTester.onTearDown();
    }

    @State("there are 5 products in the category 1")
    public void setUpStateThereAreFiveProductsInCategory1() {

    }

    @State("there is a product iPhone 8")
    public void setUpStateThereIsAProductIPhone8() throws Exception {
        long productId = 1;
        String productName = "iPhone 8";
        String manufacturer = "Apple";

        DefaultTable productTable = new DefaultTable("product",
            new Column[] {
                new Column("id", DataType.BIGINT),
                new Column("name", DataType.VARCHAR),
                new Column("manufacturer", DataType.VARCHAR)
            });
        productTable.addRow(new Object[] { productId, productName, manufacturer });
        DefaultDataSet dataSet = new DefaultDataSet();
        dataSet.addTable(productTable);

        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    @TestConfiguration
    public static class ProductCatalogueAppITConfiguration {

        @Qualifier("dataSource")
        @Autowired
        private DataSource dataSource;

        @Bean
        public IDatabaseTester getDatabaseTester() {
            return new DataSourceDatabaseTester(dataSource);
        }
    }
}
