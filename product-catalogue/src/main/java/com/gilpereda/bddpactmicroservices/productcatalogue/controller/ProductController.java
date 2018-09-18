package com.gilpereda.bddpactmicroservices.productcatalogue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gilpereda.bddpactmicroservices.productcatalogue.model.Product;
import com.gilpereda.bddpactmicroservices.productcatalogue.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(path = "/{productId}", method = RequestMethod.GET)
    public Product getProductDetail(@PathVariable("productId") final long productId) {
        return productService.findProductById(productId).orElse(null);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Product> getProducts(@RequestParam("category") final long categoryId) {
        return productService.findProductsByCategoryId(categoryId);
    }

}
