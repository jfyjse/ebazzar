package com.sayone.ebazzar.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "wish_item")
public class WishlistItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishItemId;


    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id",
            referencedColumnName = "productId")

    private ProductEntity productEntity;

    @ManyToOne
    @JoinColumn(name = "wish_id")
    @JsonIgnore
    private WishlistEntity wishlistEntity;

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public WishlistEntity getWishlistEntity() {
        return wishlistEntity;
    }

    public void setWishlistEntity(WishlistEntity wishlistEntity) {
        this.wishlistEntity = wishlistEntity;
    }
}
