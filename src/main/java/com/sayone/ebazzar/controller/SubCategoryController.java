package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.common.RestResources;
import com.sayone.ebazzar.entity.ProductEntity;
import com.sayone.ebazzar.entity.SubCategoryEntity;
import com.sayone.ebazzar.service.SubCategoryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(RestResources.GET_ALL_SUBCATEGORY)
public class SubCategoryController {
    @Autowired
    SubCategoryService subCategoryService;

    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping
    public List<SubCategoryEntity> getsubcategory(){
        return subCategoryService.getsubcategory();
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping(RestResources.GET_PRODUCTS_BY_SUBCATEGORY)
    public List<ProductEntity> getProduts(@PathVariable("name") String name){
        return subCategoryService.getproducts(name);
    }
}
