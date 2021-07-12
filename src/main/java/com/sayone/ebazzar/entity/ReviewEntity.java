package com.sayone.ebazzar.entity;

import com.sayone.ebazzar.entity.base.AuditableEntity;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
public class ReviewEntity extends AuditableEntity {

    private Integer rating;
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "productId")
    private ProductEntity productEntity;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private UserEntity userEntity;

    public ReviewEntity() {
    }

    public ReviewEntity(ProductEntity productEntity, UserEntity userEntity, Integer rating, String description) {
        this.productEntity = productEntity;
        this.userEntity = userEntity;
        this.rating = rating;
        this.description = description;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
