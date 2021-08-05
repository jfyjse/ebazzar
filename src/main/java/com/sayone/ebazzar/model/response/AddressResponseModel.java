package com.sayone.ebazzar.model.response;
public class AddressResponseModel{
    private String lane;
    private String city;
    private String zip;
    private String street;

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
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
}
