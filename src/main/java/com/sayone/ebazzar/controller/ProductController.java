package com.sayone.ebazzar.controller;


import com.sayone.ebazzar.Exception.ErrorMessages;
import com.sayone.ebazzar.Exception.RequestException;
import com.sayone.ebazzar.dto.ProductDto;
import com.sayone.ebazzar.entity.ProductEntity;
import com.sayone.ebazzar.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/product")

public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<ProductEntity>> getProduct() throws Exception {
        List<ProductEntity> products = productService.getProduct();
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            System.out.println(products);
            return ResponseEntity.of(Optional.of(products));
        }
    }


    @GetMapping("/{name}")
    public ResponseEntity<Optional<ProductEntity>> getProductById(@PathVariable("name") String name) {
        Optional<ProductEntity> product = productService.getProductById(name);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.of(Optional.of(product));
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto addProduct) throws RequestException {

            try{
                ProductDto product = productService.addProduct(addProduct);
                return ResponseEntity.status(HttpStatus.CREATED).body(product);
            } catch (Exception e) {
                e.printStackTrace();
                throw  new RequestException(ErrorMessages.MISSING_REQURED_FIELD.getErrorMessages());
            }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto body,@PathVariable Long id){
        try {
            ProductDto product = productService.updateProduct(body,id);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RequestException((ErrorMessages.MISSING_REQURED_FIELD.getErrorMessages()));
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        try {
            this.productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }
}
