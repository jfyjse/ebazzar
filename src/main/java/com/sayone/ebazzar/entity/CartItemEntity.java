package com.sayone.ebazzar.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "cartitem")
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id",
            referencedColumnName = "productId")
    private ProductEntity productEntity;

    private int quantity;
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private CartEntity cartEntity;

    public CartItemEntity(){
    }

    public CartItemEntity(ProductEntity productEntity,int quantity,CartEntity cartEntity) {
        this.productEntity = productEntity;
        this.cartEntity = cartEntity;
        this.quantity = quantity;
        this.totalPrice = (this.quantity * this.productEntity.getPrice());
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public CartEntity getCartEntity() {
        return cartEntity;
    }

    public void setCartEntity(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }

    @Override
    public String toString() {
        return "CartItem [product=" + productEntity + ", quantity=" + quantity + ", totalPrice=" + totalPrice + "]";
    }
}