package com.sayone.ebazzar.service;

import com.sayone.ebazzar.dto.ProductDto;
import com.sayone.ebazzar.entity.ProductEntity;
import com.sayone.ebazzar.repository.ProductRepository;
import com.sayone.ebazzar.repository.SubCategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    @Autowired
    ProductRepository productRepository;
    @Autowired
    SubCategoryRepository subCategoryRepository;

    public List<ProductEntity> getProduct(){
        return productRepository.findAll();
    }

    public Optional<ProductEntity> getProductById(Long id){

        return productRepository.findById(id);
    }

    public ProductDto addProductt(ProductDto addProduct){
        ProductEntity productEntity =new ProductEntity();
        ProductDto returnValue = new ProductDto();
        BeanUtils.copyProperties(addProduct, productEntity);
        productEntity.setSubCategory(subCategoryRepository.findBySubCategoryName(addProduct.getSubCategoryName()));
        ProductEntity saveProductEntity = productRepository.save(productEntity);
        BeanUtils.copyProperties(saveProductEntity,returnValue);
        return returnValue;
    }

    public ProductDto updateProduct(ProductDto body){
        ProductEntity updateProductEntity = new ProductEntity();
        BeanUtils.copyProperties(body, updateProductEntity);
        productRepository.save(updateProductEntity);

        ProductDto returnValue= new ProductDto();
        BeanUtils.copyProperties(updateProductEntity,returnValue);
        return returnValue;
    }

    public String deleteProduct(Long id){
        try{
        productRepository.deleteById(id);
        return "DELETED";}
        catch (Exception e){
            System.out.println(e);
        }
        return "ERROR";
    }
}
