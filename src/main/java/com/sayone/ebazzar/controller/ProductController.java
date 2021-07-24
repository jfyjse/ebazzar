package com.sayone.ebazzar.controller;


import com.sayone.ebazzar.common.RestResources;
import com.sayone.ebazzar.exception.ErrorMessages;
import com.sayone.ebazzar.exception.RequestException;
import com.sayone.ebazzar.dto.ProductDto;
import com.sayone.ebazzar.entity.ProductEntity;
import com.sayone.ebazzar.service.ProductService;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(RestResources.GET_ALL_PRODUCTS)

public class ProductController {
    @Autowired
    ProductService productService;

    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<ProductDto>> getProduct(@RequestParam(value = "page", defaultValue = "0")int page, @RequestParam(value = "limit",defaultValue = "2")int limit,@RequestParam(value = "sortby",defaultValue = "productName")String sortBy) throws Exception {
        Page<ProductEntity> products = productService.getProduct(page,limit,sortBy);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            List<ProductDto>returnValue = new ArrayList<>();
            for(ProductEntity productValue: products){
                ProductDto product = new ProductDto();
                product.setSubCategoryName(productValue.getSubCategory().getSubCategoryName());
                BeanUtils.copyProperties(productValue,product);
                returnValue.add(product);
            }
            return ResponseEntity.of(Optional.of(returnValue));
        }
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping(RestResources.GET_PRODUCT_BY_NAME)
    public ResponseEntity<Optional<ProductEntity>> getProductById(@PathVariable("name") String name) {
        Optional<ProductEntity> product = productService.getProductByName(name);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.of(Optional.of(product));
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @PostMapping
    @ResponseBody
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto addProduct) throws RequestException {

        try{
            ProductDto product = productService.addProduct(addProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RequestException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessages());
        }
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @PutMapping(RestResources.UPDATE_PRODUCT)
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto body,@PathVariable Long id){
        try {
            ProductDto product = productService.updateProduct(body,id);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RequestException((ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessages()));
        }

    }
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @DeleteMapping(RestResources.DELETE_PRODUCT)
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
