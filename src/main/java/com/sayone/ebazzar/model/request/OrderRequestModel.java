package com.sayone.ebazzar.model.request;

import com.sayone.ebazzar.dto.AddressDto;
import com.sayone.ebazzar.dto.ProductDto;
import com.sayone.ebazzar.dto.UserDto;

import java.util.List;

public class OrderRequestModel {

    private Long cartId;
    private Long shippingAddress;
    private Long billingAddress;

    public OrderRequestModel() {
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Long shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Long getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Long billingAddress) {
        this.billingAddress = billingAddress;
    }


}
