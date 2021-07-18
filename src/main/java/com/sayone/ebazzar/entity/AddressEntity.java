package com.sayone.ebazzar.entity;


import javax.persistence.*;

@Entity(name="addressEntity")
public class AddressEntity {

    @Id
    @GeneratedValue
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


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private UserEntity user;

    public AddressEntity() {
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

    public AddressEntity(String lane, String street, String city, String zip, String type, UserEntity user) {
        this.lane = lane;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.type = type;
        this.user = user;
    }
}
