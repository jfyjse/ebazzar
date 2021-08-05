package com.sayone.ebazzar.dto;

import java.io.Serializable;

public class OrderDto implements Serializable {

    private Long orderId;
    private String orderStatus;
    private int orderAmount;
    private AddressDto shippingAddress;
    private AddressDto billingAddress;
    private CartDto cartDto;

    public OrderDto() {
    }

    public OrderDto(String orderStatus, int orderAmount, AddressDto shippingAddress, AddressDto billingAddress, CartDto cartDto) {
        this.orderStatus = orderStatus;
        this.orderAmount = orderAmount;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.cartDto = cartDto;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public AddressDto getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressDto shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public AddressDto getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressDto billingAddress) {
        this.billingAddress = billingAddress;
    }

    public CartDto getCartDto() {
        return cartDto;
    }

    public void setCartDto(CartDto cartDto) {
        this.cartDto = cartDto;
    }
}
