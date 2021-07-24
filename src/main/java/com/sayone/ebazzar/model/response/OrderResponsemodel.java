package com.sayone.ebazzar.model.response;

public class OrderResponsemodel {
    private Long orderId;
    private String orderStatus;
    private double orderAmount;
    private AddressResponseModel shippingAddress;

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

}
