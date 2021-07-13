package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.dto.ProductDto;
import com.sayone.ebazzar.entity.Product;
import com.sayone.ebazzar.repository.ProductRepository;
import com.sayone.ebazzar.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping
    public List<Product> getProduct(){ return productService.getProduct(); }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id){
        System.out.println(id);
        return productService.getProductById(id);
    }

    @PostMapping
    @ResponseBody
    public ProductDto addProduct(@RequestBody ProductDto addProduct){
        return productService.addProductt(addProduct); }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@RequestBody ProductDto body){
        return productService.updateProduct(body);
    }
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id){
        return productService.deleteProduct(id);
    }
}
