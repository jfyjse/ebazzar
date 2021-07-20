package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.dto.CategoryDto;
import com.sayone.ebazzar.entity.Category;
import com.sayone.ebazzar.repository.CategoryRepository;
import com.sayone.ebazzar.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService service;

    @GetMapping
    public List<Category> get(){
        return service.getCategory();

    }


}
