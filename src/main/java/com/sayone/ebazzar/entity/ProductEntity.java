package com.sayone.ebazzar.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ProductEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(nullable = false)
    private String productName;
    private String description;
    @Column(nullable = false)
    private Integer price;
//    private String subCategoryName;
    @ManyToOne
    public SubCategory subCategory;

    public SubCategory getSubCategory() {
        return subCategory;
    }
    private Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }
//    public String getSubCategoryName() {
//        return subCategoryName;
//    }
//
//    public void setSubCategoryName(String subCategoryName) {
//        this.subCategoryName = subCategoryName;
//    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public ProductEntity() {
    }

    public ProductEntity(String productName, Integer price, String description, Integer quantity) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
}
