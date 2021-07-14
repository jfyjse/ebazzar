package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.entity.ProductEntity;
import com.sayone.ebazzar.entity.SubCategoryEntity;
import com.sayone.ebazzar.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/subcategory")
public class SubCategoryController {
    @Autowired
    SubCategoryService subCategoryService;

    @GetMapping
    public List<SubCategoryEntity> getsubcategory(){
        return subCategoryService.getsubcategory();
    }

    @GetMapping("/{name}")
    public List<ProductEntity> getProduts(@PathVariable("name") String name){
        return subCategoryService.getproducts(name);
    }
}
