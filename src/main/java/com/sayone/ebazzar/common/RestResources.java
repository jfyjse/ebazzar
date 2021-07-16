package com.sayone.ebazzar.common;

public interface RestResources {

    /*
     * API Endpoints for Customer related operations
     * */
    String CUSTOMER_ROOT = "/customer";
    String CUSTOMER_POST_REGISTER = "/register";
    String CUSTOMER_POST_LOGIN = "/login";
    String CUSTOMER_DELETE_LOGOUT = "/logout";
    String CUSTOMER_DELETE_BY_ID = "/{id}";
    String CUSTOMER_GET_ME = "/{id}";

    /*
     * API Endpoints for ProductEntity related operations
     * */

    String PRODUCT_ROOT = "/property";
    String PRODUCT_POST_SAVE = "/create";
    String PRODUCT_PUT_UPDATE = "/update/{id}";
    String PRODUCT_GET_SEARCH = "/search";
}
