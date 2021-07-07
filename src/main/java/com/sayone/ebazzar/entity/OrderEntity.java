package com.sayone.ebazzar.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false, length = 120)
    private String orderStatus;

    @Column(nullable = false)
    private int orderAmount;

    @ManyToOne
    private UserEntity userEntity;

    @OneToOne(targetEntity = AddressEntity.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address")
    private AddressEntity billingAddress;

    @OneToOne(targetEntity = AddressEntity.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address")
    private AddressEntity shippingAddress;

//    @OneToMany(targetEntity = ProductEntity.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
//    private List<ProductEntity> productEntityList;

    public OrderEntity() {
    }

    public OrderEntity(Long orderId, String orderStatus, int orderAmount,
                       UserEntity userEntity, AddressEntity billingAddress,
                       AddressEntity shippingAddress,List<ProductEntity> productEntityList) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderAmount = orderAmount;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.userEntity = userEntity;
      //  this.productEntityList = productEntityList;
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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

//    public List<ProductEntity> getProductEntityList() {
//        return productEntityList;
//    }
//
//    public void setProductEntityList(List<ProductEntity> productEntityList) {
//        this.productEntityList = productEntityList;
//    }

//    @Override
//    public String toString() {
//        return "OrderEntity{" + "orderId=" + orderId + ", orderStatus='" + orderStatus +
//                '\'' + ", orderAmount=" + orderAmount + ", userEntity=" + userEntity +
//                ", billingAddress=" + billingAddress + ", shippingAddress=" + shippingAddress +
//                ", productEntityList=" + productEntityList + '}';
//    }
}


