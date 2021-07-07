package com.sayone.ebazzar.model.response;

public class ReviewResponseModel {

    private Long reviewId;
    private Integer rating;
    private String description;

    public ReviewResponseModel() {
    }

    public ReviewResponseModel(Long reviewId, Integer rating, String description) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.description = description;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
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
