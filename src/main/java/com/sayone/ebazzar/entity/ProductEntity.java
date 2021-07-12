package com.sayone.ebazzar.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products")
public class ProductEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(nullable = false)
    private String productName;
    private String description;
    @Column(nullable = false)
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private  CartEntity cartEntity;

    public ProductEntity() {
    }

    public ProductEntity(String productName, String description, Integer price,CartEntity cartEntity) {
        this.productName = productName;
        this.description = description;
        this.cartEntity = cartEntity;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
