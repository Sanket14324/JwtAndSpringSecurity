package com.spring.jwttoken.security.controller;


import com.spring.jwttoken.security.model.Product;
import com.spring.jwttoken.security.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {


    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Product> saveProduct(Product product){

        Product savedProduct = productService.addProduct(product);

        return ResponseEntity.ok(savedProduct);

    }



    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("/all")
    public List<Product> getAllProduct(){

        return productService.getListOfProducts();
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable String id){

        Product product = productService.deleteProductById(id);

        return ResponseEntity.ok(product);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id){

        Product product = productService.getProductById(id);

        return ResponseEntity.ok(product);
    }
}
