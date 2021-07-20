package com.sayone.ebazzar.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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
    private Integer quantity;
    @UpdateTimestamp
    private LocalDateTime updatedTime;
    @CreationTimestamp
    private LocalDateTime createTime;

    @ManyToOne(targetEntity = SubCategoryEntity.class)
    @JoinColumn(name ="subcategory_id")
    public SubCategoryEntity subCategory;

    public ProductEntity() {

    }

    public SubCategoryEntity getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategoryEntity subCategory) {

        this.subCategory = subCategory;

    }

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
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public ProductEntity(String productName, Integer price, String description, Integer quantity) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

}

