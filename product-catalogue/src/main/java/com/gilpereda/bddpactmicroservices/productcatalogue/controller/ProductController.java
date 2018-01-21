package com.gilpereda.bddpactmicroservices.productcatalogue.controller;

import com.gilpereda.bddpactmicroservices.productcatalogue.model.Product;
import com.gilpereda.bddpactmicroservices.productcatalogue.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(path = "/{productId}", method = RequestMethod.GET)
    public Product getProductDetail(@PathVariable("productId") final long productId) {
        return productService.findProductById(productId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Product> getProducts(@RequestParam("category") final long categoryId) {
        return productService.findProductsByCategoryId(categoryId);
    }

}
