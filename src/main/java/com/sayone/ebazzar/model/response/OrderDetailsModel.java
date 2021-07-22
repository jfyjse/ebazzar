package com.sayone.ebazzar.model.response;

import java.util.List;

public class OrderDetailsModel {

    private Long orderId;
    private String orderStatus;
    private double orderAmount;
    private AddressResponseModel shippingAddress;
    private AddressResponseModel billingAddress;
    private List<CartItemDetails> cartItemDetailsList;

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

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public AddressResponseModel getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressResponseModel shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public AddressResponseModel getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressResponseModel billingAddress) {
        this.billingAddress = billingAddress;
    }

    public List<CartItemDetails> getCartItemDetailsList() {
        return cartItemDetailsList;
    }

    public void setCartItemDetailsList(List<CartItemDetails> cartItemDetailsList) {
        this.cartItemDetailsList = cartItemDetailsList;
    }
}
