package com.sayone.ebazzar.dto;
public class AddressDto {
    private static final long serialVersionUID= 7972379581449181725L;
    private Long addressId;
    private String lane;
    private  String street;
    private String city;
    private String zip;
    private String type;
    private UserDto user;
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
    public UserDto getUser() {
        return user;
    }
    public void setUser(UserDto user) {
        this.user = user;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
}
