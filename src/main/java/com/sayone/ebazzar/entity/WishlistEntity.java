package com.sayone.ebazzar.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "wishlist")
public class WishlistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wishlistId;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity userEntity;

    @OneToMany(targetEntity = WishlistItemEntity.class,cascade = CascadeType.MERGE)
    @JoinColumn(name = "wish_id", referencedColumnName = "wishlistId")
    private List<WishlistItemEntity> wishlistItemEntityList;


    public List<WishlistItemEntity> getWishlistItemEntityList() {
        return wishlistItemEntityList;
    }

    public void setWishlistItemEntityList(List<WishlistItemEntity> wishlistItemEntityList) {
        this.wishlistItemEntityList = wishlistItemEntityList;
    }

    public long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

}
