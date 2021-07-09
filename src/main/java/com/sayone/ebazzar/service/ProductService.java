package com.sayone.ebazzar.service;

import com.sayone.ebazzar.dto.ProductDto;
import com.sayone.ebazzar.entity.Product;
import com.sayone.ebazzar.entity.SubCategory;
import com.sayone.ebazzar.repository.ProductRepository;
import com.sayone.ebazzar.repository.SubCategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    @Autowired
    ProductRepository productRepository;
    @Autowired
    SubCategoryRepository subCategoryRepository;

    public List<Product> getProduct(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id){

        return productRepository.findById(id);
    }

    public ProductDto addProductt(ProductDto addProduct){
        Product product=new Product();
        ProductDto returnValue = new ProductDto();
        BeanUtils.copyProperties(addProduct,product);
        product.setSubCategory(subCategoryRepository.findBySubCategoryName(addProduct.getSubCategoryName()));
        Product saveProduct = productRepository.save(product);
        BeanUtils.copyProperties(saveProduct,returnValue);
        return returnValue;
    }

    public ProductDto updateProduct(ProductDto body){
        Product updateProduct = new Product();
        BeanUtils.copyProperties(body,updateProduct);
        productRepository.save(updateProduct);

        ProductDto returnValue= new ProductDto();
        BeanUtils.copyProperties(updateProduct,returnValue);
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
