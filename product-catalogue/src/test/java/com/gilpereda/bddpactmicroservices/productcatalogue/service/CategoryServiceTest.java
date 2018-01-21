package com.gilpereda.bddpactmicroservices.productcatalogue.service;

import com.gilpereda.bddpactmicroservices.productcatalogue.model.Category;
import com.gilpereda.bddpactmicroservices.productcatalogue.persistence.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnTheCategories() {
        List<Category> categories = getCategories();
        when(categoryRepository.findAll()).thenReturn(categories);
        assertThat(categoryService.findAll()).isEqualTo(categories);
    }

    private List<Category> getCategories() {
        return IntStream.range(0,5)
            .boxed()
            .map(categoryId -> {
                Category category = new Category();
                category.setId(categoryId);
                category.setName("Category " + categoryId);
                return category;

            })
            .collect(Collectors.toList());
    }
}
