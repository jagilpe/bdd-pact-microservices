package com.gilpereda.bddpactmicroservices.productcatalogue.controller;

import com.gilpereda.bddpactmicroservices.productcatalogue.model.Product;
import com.gilpereda.bddpactmicroservices.productcatalogue.model.ProductFactory;
import com.gilpereda.bddpactmicroservices.productcatalogue.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ProductControllerTest {

    @Mock
    private ProductService productService;
    @InjectMocks
    private ProductController productController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnAProduct() {
        long productId = 1;
        Product product = ProductFactory.getProduct(productId);

        when(productService.findProductById(productId)).thenReturn(product);

        assertThat(productController.getProductDetail(productId)).isEqualTo(product);
    }

    @Test
    public void shouldReturnTheProductsOfACategory() {
        long categoryId = 1;
        List<Product> products = ProductFactory.getProductList(5, categoryId);

        when(productService.findProductsByCategoryId(categoryId)).thenReturn(products);
        assertThat(productController.getProducts(categoryId)).isEqualTo(products);
    }

}
