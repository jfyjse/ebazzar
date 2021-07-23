package com.sayone.ebazzar.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @CreationTimestamp
    private LocalDateTime createTime;

    @Column(nullable = false, length = 120)
    private String orderStatus;

    @Column(nullable = false)
    private double orderAmount;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "billing_address")
    private AddressEntity billingAddress;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "shipping_address")
    private AddressEntity shippingAddress;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;

    public OrderEntity() {
    }

    public OrderEntity(String orderStatus, int orderAmount, AddressEntity billingAddress,
                       AddressEntity shippingAddress, CartEntity cartEntity) {
        this.orderStatus = orderStatus;
        this.orderAmount = orderAmount;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
        this.cartEntity = cartEntity;

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

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public AddressEntity getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress (AddressEntity shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public AddressEntity getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress (AddressEntity billingAddress) {
        this.billingAddress = billingAddress;
    }

    public CartEntity getCartEntity() {
        return cartEntity;
    }

    public void setCartEntity(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }

    @Override
    public String toString() {
        return "OrderEntity{" + "orderId=" + orderId + ", orderStatus='" + orderStatus +
                ", orderAmount=" + orderAmount + ", billingAddress=" + billingAddress +
                ", shippingAddress=" + shippingAddress + ", cartEntity=" + cartEntity +'}';
    }
}


