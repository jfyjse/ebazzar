package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.document.ElasticProduct;
import com.sayone.ebazzar.service.SearchServices;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
@RequestMapping("/search")
public class SearchController {
    @Autowired
    SearchServices searchServices;

    @GetMapping("/name")
    public ElasticProduct addProductByName(@RequestParam String name){
        return searchServices.getProductByName(name);
    }
    @GetMapping("/subcategory")
    public ElasticProduct addProductBySubcategory(@RequestParam String name){
        return searchServices.getProdutBySubcategory(name);
    }
}
