package com.sayone.ebazzar.entity;

import javax.persistence.*;

@Entity
@Table(name = "cartItem")
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Product product;
    private int quantity;
    private double totalPrice;

    public CartItemEntity() {

    }

    public CartItemEntity(Product product) {
        this.product = product;
        this.quantity=1;
        this.totalPrice=product.getPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "CartItemEntity{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }


}
