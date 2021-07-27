package com.sayone.ebazzar.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
    private Integer quantity;


    public ProductEntity() {
    }

    @ManyToMany(mappedBy = "productEntityList", fetch = FetchType.LAZY)
    @JsonIgnore


    private List<WishlistEntity> wishlistEntity;

    public List<WishlistEntity> getWishlistEntity() {
        return wishlistEntity;
    }

    public void setWishlistEntity(List<WishlistEntity> wishlistEntity) {
        this.wishlistEntity = wishlistEntity;
    }

    @UpdateTimestamp
    private LocalDateTime updatedTime;
    @CreationTimestamp
    private LocalDateTime createTime;

    @ManyToOne(targetEntity = SubCategoryEntity.class)
    @JoinColumn(name ="subcategory_id")
    public SubCategoryEntity subCategory;


    public SubCategoryEntity getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategoryEntity subCategory) {

        this.subCategory = subCategory;


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
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public ProductEntity(String productName, Integer quantity, String description, Integer price) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

}

