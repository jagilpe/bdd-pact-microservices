package com.gilpereda.bddpactmicroservices.productcatalogue.controller;

import com.gilpereda.bddpactmicroservices.productcatalogue.model.Category;
import com.gilpereda.bddpactmicroservices.productcatalogue.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Category> getCategories() {
        return categoryService.findAll();
    }

}
