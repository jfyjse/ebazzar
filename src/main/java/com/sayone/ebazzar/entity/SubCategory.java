package com.sayone.ebazzar.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class SubCategory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subCategoryId;

    @Column(nullable = false,length = 100)
    private String subCategoryName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubCategory)) return false;
        SubCategory that = (SubCategory) o;
        return Objects.equals(subCategoryId, that.subCategoryId) && Objects.equals(subCategoryName, that.subCategoryName) && Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subCategoryId, subCategoryName, products);
    }

    @OneToMany(targetEntity =Product.class,mappedBy = "subCategory",cascade = CascadeType.ALL)
//    @JoinColumn(name="sub_id",referencedColumnName ="subCategoryId" )
    public List<Product> products = new ArrayList<Product>();

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public SubCategory(){

    }

    public SubCategory(String subCategoryName) {
        this.subCategoryName=subCategoryName;
    }


    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }




}
