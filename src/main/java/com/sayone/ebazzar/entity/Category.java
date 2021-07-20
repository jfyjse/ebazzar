package com.sayone.ebazzar.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false,length = 100)
    private String categoryName;

    @OneToMany(targetEntity = SubCategoryEntity.class,cascade = CascadeType.ALL)
    @JoinColumn(name="category_id",referencedColumnName = "categoryId")
    private List<SubCategoryEntity> subCategories = new ArrayList<SubCategoryEntity>();

    public List<SubCategoryEntity> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategoryEntity> subCategories) {
        this.subCategories = subCategories;
    }

    public Category(){

    }

    public Category(String categoryName){
        this.categoryName=categoryName;

    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}
