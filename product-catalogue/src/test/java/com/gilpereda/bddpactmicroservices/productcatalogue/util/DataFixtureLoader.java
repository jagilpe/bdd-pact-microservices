package com.gilpereda.bddpactmicroservices.productcatalogue.util;

import com.gilpereda.bddpactmicroservices.productcatalogue.model.Product;
import com.gilpereda.bddpactmicroservices.productcatalogue.model.ProductFactory;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.datatype.DataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataFixtureLoader {

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    private IDatabaseTester databaseTester;

    public void setUpFixture() throws Exception {
        long productId = 1;
        Product product = ProductFactory.getProduct(productId);

        DefaultTable productTable = new DefaultTable("product",
            new Column[] {
                new Column("id", DataType.BIGINT),
                new Column("name", DataType.VARCHAR),
                new Column("manufacturer", DataType.VARCHAR)
            });
        productTable.addRow(new Object[] { product.getId(), product.getName(), product.getManufacturer() });
        DefaultDataSet dataSet = new DefaultDataSet();
        dataSet.addTable(productTable);

        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    public void reset() throws Exception {
        getDatabaseTester().onTearDown();
    }

    private IDatabaseTester getDatabaseTester() {
        if (databaseTester == null) {
            databaseTester = new DataSourceDatabaseTester(dataSource);
        }
        return databaseTester;
    }
}
