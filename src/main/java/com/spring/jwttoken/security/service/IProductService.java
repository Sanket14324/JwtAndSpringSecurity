package com.spring.jwttoken.security.service;

import com.spring.jwttoken.security.model.Product;

import java.util.List;

public interface IProductService {

     Product addProduct(Product product);

    List<Product> getListOfProducts();

    Product getProductById(String id);

    Product deleteProductById(String id);

    Product updateProductById(String id, Product product);

}
