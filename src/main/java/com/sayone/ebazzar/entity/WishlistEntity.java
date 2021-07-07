package com.sayone.ebazzar.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "wishlist")
public class WishlistEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id")
    private long wishlistId;
//    private long userId;
    @OneToOne(targetEntity = Product.class,cascade = CascadeType.MERGE)
    @JoinColumn(
        name = "wishlist_id",
        referencedColumnName = "wishlistId"
      )
    private Product product;

//    @OneToOne(mappedBy = "wishlist")

//    @JoinColumn(name = "users_id", referencedColumnName = "id")
//    @JoinColumn(name="userId")
    @OneToOne(targetEntity = UserEntity.class,cascade = CascadeType.MERGE)
    @JoinColumn(
            name = "wishlist_id",
            referencedColumnName = "wishlist_id"
    )
    private UserEntity user;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
