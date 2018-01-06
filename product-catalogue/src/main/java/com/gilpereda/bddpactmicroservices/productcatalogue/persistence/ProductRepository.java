package com.gilpereda.bddpactmicroservices.productcatalogue.persistence;

import com.gilpereda.bddpactmicroservices.productcatalogue.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

    Product findOneById(long productId);

}
