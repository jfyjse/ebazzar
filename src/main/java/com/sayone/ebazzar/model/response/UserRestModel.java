package com.sayone.ebazzar.model.response;

import java.util.List;

public class UserRestModel {

    private String firstName;
    private String lastName;
    private String email;
    private List<AddressResponseModel> addressResponseModels;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AddressResponseModel> getAddressResponseModels() {
        return addressResponseModels;
    }

    public void setAddressResponseModels(List<AddressResponseModel> addressResponseModels) {
        this.addressResponseModels = addressResponseModels;
    }
}
