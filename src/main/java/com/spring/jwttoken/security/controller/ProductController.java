package com.spring.jwttoken.security.controller;


import com.spring.jwttoken.security.model.Product;
import com.spring.jwttoken.security.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductController {


    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){

        Product savedProduct = productService.addProduct(product);

        return ResponseEntity.ok(savedProduct);

    }



    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN', 'USER')")
    @GetMapping("/all")
    public List<Product> getAllProduct(){

        return productService.getListOfProducts();
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable String id){
        try{
        Product product = productService.deleteProductById(id);

        return ResponseEntity.ok(product);}
        catch (Exception e){
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("error", "Product with id "+id+"not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable String id){

        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        }
        catch (Exception e){
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("error", "Product with id "+id+"not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }


    }
}
