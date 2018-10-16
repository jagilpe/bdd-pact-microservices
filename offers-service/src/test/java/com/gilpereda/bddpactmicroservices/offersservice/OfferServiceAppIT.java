package com.gilpereda.bddpactmicroservices.offersservice;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import com.gilpereda.bddpactmicroservices.offersservice.domain.OfferFactory;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.AmbiguousTableNameException;
import org.dbunit.dataset.*;
import org.dbunit.dataset.datatype.DataType;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@RunWith(SpringRestPactRunner.class)
@Provider("offers-service")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = OfferServiceApp.class)
@PactBroker(protocol = "http", host = "127.0.0.1", port = "1080")
public class OfferServiceAppIT {

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    private IDatabaseTester databaseTester;

    @TestTarget
    public Target target = new HttpTarget(8082);

    @After
    public void tearDown() throws Exception {
        getDatabaseTester().onTearDown();
    }

    @State("there are 8 offers for product 1")
    public void setUpStateThereAre8OffersForProduct1() throws Exception {
        long productId = 1;
        int count = 8;
        IDataSet dataset = getDataset(productId, count);

        getDatabaseTester().setDataSet(dataset);
        getDatabaseTester().onSetup();
    }

    private IDatabaseTester getDatabaseTester() {
        if (databaseTester == null) {
            databaseTester = new DataSourceDatabaseTester(dataSource);
        }
        return databaseTester;
    }

    private IDataSet getDataset(long productId, int count) throws AmbiguousTableNameException {
        final DefaultTable offerTable = new DefaultTable("offer",
            new Column[] {
                new Column("id", DataType.BIGINT),
                new Column("product_id", DataType.BIGINT),
                new Column("shop_id", DataType.BIGINT),
                new Column("shop_name", DataType.VARCHAR),
                new Column("price", DataType.INTEGER)
            });
        OfferFactory.getOffers(productId, count)
            .forEach(offer -> {
                try {
                    offerTable.addRow(new Object[] {
                        offer.getId(),
                        offer.getProductId(),
                        offer.getShopId(),
                        offer.getShopName(),
                        offer.getPrice() });
                } catch (DataSetException e) {
                    e.printStackTrace();
                }
            });
        DefaultDataSet dataSet = new DefaultDataSet();
        dataSet.addTable(offerTable);
        return dataSet;
    }
}
