package com.gilpereda.bddpactmicroservices.productcatalogue.service;

import java.util.Collections;
import java.util.Optional;

import com.gilpereda.bddpactmicroservices.productcatalogue.model.Category;
import com.gilpereda.bddpactmicroservices.productcatalogue.model.Product;
import com.gilpereda.bddpactmicroservices.productcatalogue.persistence.CategoryRepository;
import com.gilpereda.bddpactmicroservices.productcatalogue.persistence.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public Optional<Product> findProductById(final long productId) {
        return productRepository.findById(productId);
    }

    public Iterable<Product> findProductsByCategoryId(long categoryId) {
        return categoryRepository.findById(categoryId)
            .map(productRepository::findAllByCategory)
            .orElse(Collections.emptyList());
    }
}
