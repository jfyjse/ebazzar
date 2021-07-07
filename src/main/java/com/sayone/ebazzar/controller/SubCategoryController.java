package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.entity.SubCategory;
import com.sayone.ebazzar.repository.SubCategoryRepository;
import com.sayone.ebazzar.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.GeneratedValue;
import java.util.List;


@RestController
@RequestMapping("/subcategory")
public class SubCategoryController {
    @Autowired
    SubCategoryService subCategoryService;

    @GetMapping
    public List<SubCategory> getsubcategory(){
        return subCategoryService.getsubcategory();
    }

}
