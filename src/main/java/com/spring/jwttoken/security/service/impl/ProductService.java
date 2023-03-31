package com.spring.jwttoken.security.service.impl;


import com.spring.jwttoken.security.model.Product;
import com.spring.jwttoken.security.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;
    public Product addProduct(Product product){
        Product savedProduct = productRepository.save(product);
        System.out.println(product.getName()+"---------"+product.getPrice());
        System.out.println(savedProduct.getName()+"---------"+savedProduct.getPrice());
        return product;
    }

    public List<Product> getListOfProducts(){
        System.out.println(productRepository);
        return productRepository.findAll();
    }

    public Product getProductById(String id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product with "+id+" not found"));
    }

    public Product deleteProductById(String id ){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with Id: "+id));

        productRepository.delete(product);
        return product;
    }

}
