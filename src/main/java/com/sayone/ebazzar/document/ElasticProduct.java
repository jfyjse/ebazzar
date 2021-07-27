package com.sayone.ebazzar.document;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Document(indexName = "product")
public class ElasticProduct {

    @Id
    @Field(type = FieldType.Auto)
    private Long id;

    @Field(type = FieldType.Auto)
    private String name;

    @Field(type = FieldType.Auto)
    private String description;

    @Field(type = FieldType.Auto)
    private String price;

    @Field(type = FieldType.Auto)
    private String quantity;

    @Field(type = FieldType.Auto)
    private String subcategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }



}