package com.sayone.ebazzar.dto;

import java.util.List;

public class OrderDto {

    private Long orderId;
    private String orderStatus;
    private Float orderAmount;
//    private UserDto userDto;
//    private List<AddressDto> addressDtoList;
//    private List<ProductDto> productDtoList;

//    public OrderDto(Long orderId, String orderStatus, Float orderAmount,
//                    UserDto userDto, List<AddressDto> addressDtoList,
//                    List<ProductDto> productDtoList) {
//        this.orderId = orderId;
//        this.orderStatus = orderStatus;
//        this.orderAmount = orderAmount;
//        this.userDto = userDto;
//        this.addressDtoList = addressDtoList;
//        this.productDtoList = productDtoList;
//    }

    public OrderDto() {
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

    public Float getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Float orderAmount) {
        this.orderAmount = orderAmount;
    }

//    public UserDto getUserDto() {
//        return userDto;
//    }
//
//    public void setUserDto(UserDto userDto) {
//        this.userDto = userDto;
//    }
//
//    public List<AddressDto> getAddressDtoList() {
//        return addressDtoList;
//    }
//
//    public void setAddressDtoList(List<AddressDto> addressDtoList) {
//        this.addressDtoList = addressDtoList;
//    }
//
//    public List<ProductDto> getProductDtoList() {
//        return productDtoList;
//    }
//
//    public void setProductDtoList(List<ProductDto> productDtoList) {
//        this.productDtoList = productDtoList;
//    }
//
//    @Override
//    public String toString() {
//        return "OrderDto{" + "orderId=" + orderId + ", orderStatus='" + orderStatus + '\'' + ", orderAmount=" + orderAmount +
//                ", userDto=" + userDto + ", billingAndShipping=" + addressDtoList + ", productDtoList=" + productDtoList + '}';
//    }
}
