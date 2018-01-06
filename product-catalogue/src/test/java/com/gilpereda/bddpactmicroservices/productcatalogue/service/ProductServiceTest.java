package com.gilpereda.bddpactmicroservices.productcatalogue.service;

import com.gilpereda.bddpactmicroservices.productcatalogue.model.Product;
import com.gilpereda.bddpactmicroservices.productcatalogue.persistence.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindAProductByItsId() {
        long productId = 1;
        Product product = new Product();
        product.setId(productId);
        product.setName("Product 1");
        product.setManufacturer("Manufacturer 1");

        when(productRepository.findOneById(productId)).thenReturn(product);
        assertThat(productService.findProductById(productId)).isEqualTo(product);
    }
}
