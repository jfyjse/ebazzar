package com.sayone.ebazzar.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
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

    private UserEntity userDetails;


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

    public UserEntity getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
    }
}
