package com.gilpereda.bddpactmicroservices.productcatalogue.controller;

import com.gilpereda.bddpactmicroservices.productcatalogue.model.Category;
import com.gilpereda.bddpactmicroservices.productcatalogue.model.TestDataFactory;
import com.gilpereda.bddpactmicroservices.productcatalogue.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;
    @InjectMocks
    private CategoryController categoryController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnTheCategories() {
        List<Category> categories = TestDataFactory.getCategories(6);
        when(categoryService.findAll()).thenReturn(categories);
        assertThat(categoryController.getCategories()).isEqualTo(categories);
    }

}
