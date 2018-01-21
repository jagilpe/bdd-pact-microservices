package com.gilpereda.bddpactmicroservices.productcatalogue.service;

import com.gilpereda.bddpactmicroservices.productcatalogue.model.Category;
import com.gilpereda.bddpactmicroservices.productcatalogue.persistence.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }
}
