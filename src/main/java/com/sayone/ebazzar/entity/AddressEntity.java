package com.sayone.ebazzar.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","user"})
@Table(name="address")
public class AddressEntity implements Serializable {
    private static final long serialVersionUID= -4988491775034367092L;
    @Id
    @SequenceGenerator(
            name="address_sequence",
            sequenceName = "address_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "address_sequence"
    )
    private Long addressId;
    @Column(nullable = false,length = 120)
    private String lane;
    @Column(nullable = false,length = 120)
    private  String street;
    @Column(nullable = false,length = 120)
    private String city;
    @Column(nullable = false,length = 15)
    private String zip;
    @Column(nullable = false,length = 20)
    private String type;
    @UpdateTimestamp
    private LocalDateTime updatedTime;
    @CreationTimestamp
    private LocalDateTime createTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    public AddressEntity() {
    }
    public AddressEntity(String lane, String street, String city, String zip, String type, UserEntity user) {
        this.lane = lane;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.type = type;
        this.user = user;
    }
    public Long getAddressId() {
        return addressId;
    }
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
    public String getLane() {
        return lane;
    }
    public void setLane(String lane) {
        this.lane = lane;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getZip() {
        return zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }
}
