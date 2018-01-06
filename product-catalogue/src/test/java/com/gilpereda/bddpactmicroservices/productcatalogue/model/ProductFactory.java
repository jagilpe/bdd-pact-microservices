package com.gilpereda.bddpactmicroservices.productcatalogue.model;

public class ProductFactory {

    public static Product getProduct(final long productId) {
        Product product = new Product();
        product.setId(productId);
        product.setName("Product 1");
        product.setManufacturer("Manufacturer 1");
        return product;
    }
}
