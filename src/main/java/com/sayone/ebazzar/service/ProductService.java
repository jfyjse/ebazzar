package com.sayone.ebazzar.service;

import com.sayone.ebazzar.dto.ProductDto;
import com.sayone.ebazzar.entity.ProductEntity;
import com.sayone.ebazzar.entity.SubCategoryEntity;
import com.sayone.ebazzar.repository.ProductRepository;
import com.sayone.ebazzar.repository.SubCategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class ProductService {


    @Autowired
    ProductRepository productRepository;
    @Autowired
    SubCategoryRepository subCategoryRepository;

    public Page<ProductEntity> getProduct(int page, int limit,String sortBy) {
        Sort sort=Sort.by(Sort.Direction.ASC,sortBy);
        Pageable pageableRequest = PageRequest.of(page,limit,sort);
        Page<ProductEntity> productpage = productRepository.findAll(pageableRequest);
        return productpage;
    }

    public Optional<ProductEntity> getProductById(String name) {

        return productRepository.findByProductName(name);
    }


    public ProductDto addProduct(ProductDto addProduct) {
        ProductEntity product = new ProductEntity();
        ProductDto returnValue = new ProductDto();

        SubCategoryEntity getSubcategoryName = subCategoryRepository.findBySubCategoryName(addProduct.getSubCategoryName());
        if(getSubcategoryName != null) {
            product.setSubCategory(getSubcategoryName);
            product.setProductName(addProduct.getProductName());
            product.setPrice(addProduct.getPrice());
            product.setQuantity(addProduct.getQuantity());
            product.setDescription(addProduct.getDescription());
            ProductEntity saveProduct = productRepository.save(product);
            BeanUtils.copyProperties(saveProduct, returnValue);
            returnValue.setSubCategoryName(saveProduct.getSubCategory().getSubCategoryName());
            return returnValue;
        } else {
            product.setSubCategory(subCategoryRepository.findBySubCategoryName("Others"));
            product.setProductName(addProduct.getProductName());
            product.setPrice(addProduct.getPrice());
            product.setQuantity(addProduct.getQuantity());
            product.setDescription(addProduct.getDescription());
            ProductEntity saveProduct = productRepository.save(product);
            BeanUtils.copyProperties(saveProduct, returnValue);
            returnValue.setSubCategoryName("Others");
            return returnValue;
        }
    }


    public ProductDto updateProduct(ProductDto body, Long Id) {
        ProductEntity product = new ProductEntity();
        BeanUtils.copyProperties(body, product);
        ProductEntity productToUpdate = productRepository.findByProductId(Id);
        productToUpdate.setSubCategory(subCategoryRepository.findBySubCategoryName(body.getSubCategoryName()));
        productToUpdate.setProductName(body.getProductName());
        productToUpdate.setDescription(body.getDescription());
        productToUpdate.setPrice(body.getPrice());
        productToUpdate.setQuantity(body.getQuantity());
        productRepository.save(productToUpdate);


        ProductDto returnValue = new ProductDto();
        BeanUtils.copyProperties(productToUpdate, returnValue);
        returnValue.setSubCategoryName(productToUpdate.getSubCategory().getSubCategoryName());
        return returnValue;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
