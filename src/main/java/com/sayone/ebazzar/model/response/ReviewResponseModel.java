package com.sayone.ebazzar.model.response;

public class ReviewResponseModel {

    private Integer rating;
    private String description;
    private String productName;
    private String productDescription;

    public ReviewResponseModel() {
    }

    public ReviewResponseModel(Integer rating, String description, String productName, String productDescription, String userEmail) {
        this.rating = rating;
        this.description = description;
        this.productName = productName;
        this.productDescription = productDescription;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

}
