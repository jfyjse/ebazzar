package com.sayone.ebazzar.dto;

import java.io.Serializable;

public class ProductDto implements Serializable {
    private Long productId;
    private String productName;
    private String description;
    private Integer price;
    private Integer quantity;

    private String subCategoryName;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {

        this.subCategoryName = subCategoryName;
    }


    public ProductDto() {
    }

    public ProductDto(String productName, String description, Integer price) {
        this.productName = productName;
        this.description = description;
        this.price = price;
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

    @Override
    public String toString() {
        return "ProductDto{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", subCategoryName=" + subCategoryName +
                '}';
    }
}
