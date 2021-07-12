package com.sayone.ebazzar.entity.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sayone.ebazzar.common.CustomDateSerializer;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public class AuditableEntity extends BaseEntity{

    @Column(name = "created_at", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Long createdDate;

    @Column(name = "updated_at", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Long updatedDate;

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Long updatedDate) {
        this.updatedDate = updatedDate;
    }

    @PrePersist
    @PreUpdate
    public void setCreateUpdateTime(){
        Long currentTime = System.currentTimeMillis();
        if(super.getId() == null){
            this.setCreatedDate(currentTime);
        }
        this.setUpdatedDate(currentTime);
    }
}
