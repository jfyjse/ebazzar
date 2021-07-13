package com.sayone.ebazzar.service;

import com.sayone.ebazzar.entity.SubCategory;
import com.sayone.ebazzar.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryService {
    @Autowired
    SubCategoryRepository subCategoryRepository;


    public List<SubCategory> getsubcategory(){
        return subCategoryRepository.findAll();
    }

}
