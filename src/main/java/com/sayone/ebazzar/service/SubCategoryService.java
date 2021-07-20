package com.sayone.ebazzar.service;

import com.sayone.ebazzar.entity.ProductEntity;
import com.sayone.ebazzar.entity.SubCategoryEntity;
import com.sayone.ebazzar.repository.SubCategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubCategoryService {
    @Autowired
    SubCategoryRepository subCategoryRepository;


    public List<SubCategoryEntity> getsubcategory(){
        return subCategoryRepository.findAll();
    }

    public List<ProductEntity> getproducts(String name){
        List<SubCategoryEntity> subCategoryEntity = subCategoryRepository.findAll();
        List<ProductEntity> productEntity = new ArrayList<>();
        for (SubCategoryEntity i: subCategoryEntity){
            if (i.getSubCategoryName().equals(name)){
                System.out.println("If Loop");
                for(ProductEntity product: i.getProducts()){
                    productEntity.add(product);
                }
            }
        }
        return productEntity;

    }

}
