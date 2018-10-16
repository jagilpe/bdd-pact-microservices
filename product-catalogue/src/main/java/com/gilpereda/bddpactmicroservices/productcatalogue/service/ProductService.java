package com.gilpereda.bddpactmicroservices.productcatalogue.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gilpereda.bddpactmicroservices.productcatalogue.model.Product;
import com.gilpereda.bddpactmicroservices.productcatalogue.persistence.CategoryRepository;
import com.gilpereda.bddpactmicroservices.productcatalogue.persistence.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Optional<Product> findProductById(final long productId) {
        return productRepository.findById(productId);
    }

    public Iterable<Product> findProductsByCategoryId(long categoryId) {
        return categoryRepository.findById(categoryId)
            .map(productRepository::findAllByCategory)
            .orElse(Collections.emptyList());
    }
}
