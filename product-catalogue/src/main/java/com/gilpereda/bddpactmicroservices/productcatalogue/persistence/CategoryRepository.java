package com.gilpereda.bddpactmicroservices.productcatalogue.persistence;

import com.gilpereda.bddpactmicroservices.productcatalogue.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
