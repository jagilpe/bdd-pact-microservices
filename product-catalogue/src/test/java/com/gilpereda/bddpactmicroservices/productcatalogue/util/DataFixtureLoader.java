package com.gilpereda.bddpactmicroservices.productcatalogue.util;

import com.gilpereda.bddpactmicroservices.productcatalogue.model.Category;
import com.gilpereda.bddpactmicroservices.productcatalogue.model.Product;
import com.gilpereda.bddpactmicroservices.productcatalogue.model.TestDataFactory;
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

    public void setUpFixture(final TestCase testCase) throws Exception {
        DefaultDataSet dataSet = new DefaultDataSet();
        DefaultTable productTable = new DefaultTable("product",
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
        TestFixture testFixture = getTestFixture(testCase);
        for(Category c: testFixture.categories) {
            categoryTable.addRow(new Object[] { c.getId(), c.getName() });
        }
        for(Product p: testFixture.products) {
            productTable.addRow(new Object[]{ p.getId(), p.getName(), p.getManufacturer(), p.getCategory().getId() });
        }

        dataSet.addTable(categoryTable);
        dataSet.addTable(productTable);
        getDatabaseTester().setDataSet(dataSet);
        getDatabaseTester().onSetup();
    }

    public void reset() throws Exception {
        getDatabaseTester().onTearDown();
    }

    private TestFixture getTestFixture(final TestCase testCase) {
        Category[] categories;
        Product[] products;
        switch (testCase) {
            case ONE_PRODUCT:
                long productId = 1;
                Product product = TestDataFactory.getProduct(productId);
                products = new Product[] { product };
                categories = new Category[] { product.getCategory() };
                break;
            case FIVE_PRODUCTS_IN_CATEGORY_1:
                long categoryId = 1;
                int productsCount = 5;
                categories = new Category[] { TestDataFactory.getCategory(categoryId) };
                products = TestDataFactory.getProductList(productsCount, categories[0]).toArray(new Product[productsCount]);
                break;
            case SIX_CATEGORIES:
                categories = TestDataFactory.getCategories(6).toArray(new Category[] {});
                products = new Product[0];
                break;
            default:
                categories = new Category[0];
                products = new Product[0];
                break;
        }

        return new TestFixture(categories, products);
    }

    private IDatabaseTester getDatabaseTester() {
        if (databaseTester == null) {
            databaseTester = new DataSourceDatabaseTester(dataSource);
        }
        return databaseTester;
    }

    private static final class TestFixture {
        private final Category[] categories;
        private final Product[] products;

        TestFixture(final Category[] categories, final Product[] products) {
            this.categories = categories;
            this.products = products;
        }
    }
}
