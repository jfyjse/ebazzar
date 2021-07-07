package com.sayone.ebazzar.dto;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

public class ReviewDto implements Serializable {

    private Long reviewId;
    private ProductDto productDto;
    private UserDto userDto;
    private Integer rating;
    private String description;

    public ReviewDto() {
    }

    public ReviewDto(Long reviewId, ProductDto  productDto, UserDto userDto, Integer rating, String description) {
        this.reviewId = reviewId;
        this.productDto = productDto;
        this.userDto = userDto;
        this.rating = rating;
        this.description = description;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public ProductDto  getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
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
        return "ReviewDto{" + "reviewId=" + reviewId + ", productDto=" + productDto +
                ", userDto=" + userDto + ", rating=" + rating + ", description='" + description + '\'' + '}';
    }
}
