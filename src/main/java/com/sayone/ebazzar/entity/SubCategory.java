package com.sayone.ebazzar.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class SubCategory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subCategoryId;

    @Column(nullable = false,length = 100)
    private String subCategoryName;

    public SubCategory(){

    }

    public SubCategory(String subCategoryName) {
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




}
