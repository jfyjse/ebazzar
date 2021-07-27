package com.sayone.ebazzar.service;

import com.sayone.ebazzar.document.ElasticProduct;
import com.sayone.ebazzar.repository.ProductElasticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServices {

    @Autowired
    ProductElasticRepository productElasticRepository;

    public ElasticProduct getProductByName(String name) {
        return productElasticRepository.findByName(name);
    }
    public ElasticProduct getProdutBySubcategory(String name){
        return productElasticRepository.findBysubcategory(name);
    }

}
