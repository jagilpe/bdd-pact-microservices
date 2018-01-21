package com.gilpereda.bddpactmicroservices.productcatalogue.service;

import com.gilpereda.bddpactmicroservices.productcatalogue.model.Category;
import com.gilpereda.bddpactmicroservices.productcatalogue.model.Product;
import com.gilpereda.bddpactmicroservices.productcatalogue.model.TestDataFactory;
import com.gilpereda.bddpactmicroservices.productcatalogue.persistence.CategoryRepository;
import com.gilpereda.bddpactmicroservices.productcatalogue.persistence.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindAProductByItsId() {
        long productId = 1;
        Product product = TestDataFactory.getProduct(productId);

        when(productRepository.findOne(productId)).thenReturn(product);
        assertThat(productService.findProductById(productId)).isEqualTo(product);
    }

    @Test
    public void shouldFindAllTheProductsByTheCategoryId() {
        long categoryId = 1;
        Category category = TestDataFactory.getCategory(categoryId);
        List<Product> products = TestDataFactory.getProductList(5, category);

        when(categoryRepository.findOne(categoryId)).thenReturn(category);
        when(productRepository.findAllByCategory(category)).thenReturn(products);
        assertThat(productService.findProductsByCategoryId(categoryId)).isEqualTo(products);
    }
}
