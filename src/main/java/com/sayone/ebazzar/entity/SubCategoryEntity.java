package com.sayone.ebazzar.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer","handler","products"})
public class SubCategoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subCategoryId;

    @Column(nullable = false,length = 100)
    private String subCategoryName;

    @OneToMany(targetEntity = ProductEntity.class,mappedBy = "subCategory",cascade = CascadeType.ALL)
    @JsonIgnore
    public List<ProductEntity> products = new ArrayList<ProductEntity>();

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public SubCategoryEntity(){

    }

    public SubCategoryEntity(String subCategoryName) {
        this.subCategoryName=subCategoryName;
    }


    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }


    @Override
    public String toString() {
        return "SubCategory{" +
                "subCategoryId=" + subCategoryId +
                ", subCategoryName='" + subCategoryName + '\'' +
                ", products=" + products +
                '}';
    }
}
