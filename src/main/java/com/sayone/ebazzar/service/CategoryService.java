package com.sayone.ebazzar.service;


import com.sayone.ebazzar.entity.Category;
import com.sayone.ebazzar.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public List<Category> getCategory() {
        return categoryRepository.findAll();


    }
}