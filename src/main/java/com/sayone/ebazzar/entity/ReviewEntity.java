package com.sayone.ebazzar.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reviews")
public class ReviewEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private Integer rating;
    private String description;

    // one review belongs to only one product
    // one product can have multiple reviews
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "productId")
    private ProductEntity productEntity;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private UserEntity userEntity;



    public ReviewEntity() {
    }

    public ReviewEntity(Long reviewId, ProductEntity productEntity, UserEntity userEntity, Integer rating, String description) {
        this.reviewId = reviewId;
        this.productEntity = productEntity;
        this.userEntity = userEntity;
        this.rating = rating;
        this.description = description;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
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

    @Override
    public String toString() {
        return "Review{" + "reviewId=" + reviewId + ", productEntity=" + productEntity +
                ", userEntity=" + userEntity + ", rating=" + rating + ", description='" + description + '\'' + '}';
    }
}
