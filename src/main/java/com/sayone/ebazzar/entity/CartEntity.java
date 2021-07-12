package com.sayone.ebazzar.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;
    private long userId;
    private String cartStatus;

    @OneToMany(cascade = CascadeType.ALL ,targetEntity = CartItemEntity.class)
    @JoinColumn(name="cart_id",referencedColumnName = "cartId")
    private List <CartItemEntity> cartItemEntities;

    private double grantTotal;

    public CartEntity(){
        cartItemEntities= new ArrayList<CartItemEntity>();
        this.grantTotal=0;
    }


    public CartEntity(long cartId) {
        super();
        this.cartId = cartId;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }



    public double getGrantTotal() {
        return grantTotal;
    }

    public void setGrantTotal(double grantTotal) {
        this.grantTotal = grantTotal;
    }

    public List<CartItemEntity> getCartItemEntities() {
        return cartItemEntities;
    }

    public void setCartItemEntities(List<CartItemEntity> cartItemEntities) {
        this.cartItemEntities = cartItemEntities;
    }

    public String getCartStatus() {
        return cartStatus;
    }

    public void setCartStatus(String cartStatus) {
        this.cartStatus = cartStatus;
    }
}
