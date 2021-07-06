package com.sayone.ebazzar.dto;

import javax.persistence.Column;
import java.io.Serializable;

public class subCategoryDto implements Serializable {
    private Long subCategoryId;

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

    private String subCategoryName;
}
