package com.sayone.ebazzar.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sayone.ebazzar.entity.CartEntity;
import com.sayone.ebazzar.entity.ProductEntity;

import javax.persistence.*;

public class CartItemDto {

    private Long cartItemId;
    private ProductDto productDto;
    private int quantity;
    private double totalPrice;

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
