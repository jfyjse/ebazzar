package com.sayone.ebazzar.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cart")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;

    private double totalAmount;

    private String cartStatus;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity userEntity;


    @OneToMany(targetEntity = CartItemEntity.class,cascade = CascadeType.MERGE)
    @JoinColumn(name = "cart_id", referencedColumnName = "cartId")
    private List<CartItemEntity> cartItemEntityList;

    public CartEntity() {
    }

    public CartEntity(UserEntity userEntity, String cartStatus) {
        this.userEntity = userEntity;
        this.cartStatus = cartStatus;

    }


    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public String getCartStatus() {
        return cartStatus;
    }

    public void setCartStatus(String cartStatus) {
        this.cartStatus = cartStatus;
    }

    public double getTotalAmount()
    {
        totalAmount=0.0;
        for(CartItemEntity cartItemEntity:cartItemEntityList){
            totalAmount +=  cartItemEntity.getTotalPrice();
        }
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<CartItemEntity> getCartItemEntityList() {
        return cartItemEntityList;
    }

    public void setCartItemEntityList(List<CartItemEntity> cartItemEntityList) {
        this.cartItemEntityList = cartItemEntityList;

    }
}

