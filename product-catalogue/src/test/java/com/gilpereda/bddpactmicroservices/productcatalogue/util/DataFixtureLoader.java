package com.gilpereda.bddpactmicroservices.productcatalogue.util;

import com.gilpereda.bddpactmicroservices.productcatalogue.model.Category;
import com.gilpereda.bddpactmicroservices.productcatalogue.model.DataFactory;
import com.gilpereda.bddpactmicroservices.productcatalogue.model.Product;
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
import java.util.List;

@Component
public class DataFixtureLoader {

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    private IDatabaseTester databaseTester;

    public void setUpFixture(final TestCase testCase) throws Exception {
        DefaultDataSet dataSet = new DefaultDataSet();
        long categoryId;
        Category category;
        final DefaultTable productTable = new DefaultTable("product",
            new Column[] {
                new Column("id", DataType.BIGINT),
                new Column("name", DataType.VARCHAR),
                new Column("manufacturer", DataType.VARCHAR),
                new Column("category_id", DataType.BIGINT)
            });
        DefaultTable categoryTable = new DefaultTable("category",
            new Column[] {
                new Column("id", DataType.BIGINT),
                new Column("name", DataType.VARCHAR)
            });
        switch (testCase) {
            case ONE_PRODUCT:
                long productId = 1;
                Product product = DataFactory.getProduct(productId);
                category = product.getCategory();
                categoryId = category.getId();
                categoryTable.addRow(new Object[] { categoryId, category.getName() });
                productTable.addRow(new Object[]{ product.getId(), product.getName(), product.getManufacturer(), categoryId });
                break;
            case FIVE_PRODUCTS_IN_CATEGORY_1:
                categoryId = 1;
                category = DataFactory.getCategory(categoryId);
                List<Product> products = DataFactory.getProductList(5, category);
                categoryTable.addRow(new Object[] { category.getId(), category.getName() });
                for (Product p : products) {
                    productTable.addRow(new Object[]{ p.getId(), p.getName(), p.getManufacturer(), categoryId });
                }
                break;
            case SIX_CATEGORIES:
                for(Category cat: DataFactory.getCategories(6)) {
                    categoryTable.addRow(new Object[] { cat.getId(), cat.getName()});
                }
                break;
        }

        dataSet.addTable(categoryTable);
        dataSet.addTable(productTable);
        getDatabaseTester().setDataSet(dataSet);
        getDatabaseTester().onSetup();
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
