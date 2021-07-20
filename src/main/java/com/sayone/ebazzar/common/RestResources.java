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
     * API Endpoints for Product related operations
     * */

    String PRODUCT_ROOT = "/property";
    String PRODUCT_POST_SAVE = "/create";
    String PRODUCT_PUT_UPDATE = "/update/{id}";
    String PRODUCT_GET_SEARCH = "/search";

    /*
     * API Endpoints for Review related operations
     * */
    String REVIEW_ROOT = "/reviews";
    String UPDATE_RATING_BY_ID ="/update/{reviewId}";
    String GET_RATING_PID="/{pid}";
    String UPDATE_RATING_BY_PRODUCT="{pid}";
    String DELETE_REVIEW="/delete";

    /*
     * API Endpoints for Order related operations
     * */
    String ORDER_ROOT = "/orders";
    String GET_ORDER_BY_ID = "/{id}";
    String VIEW_ALL_ORDERS="/users/{userid}";
    String UPDATE_ORDER_STATUS="/{orderId}";
    String GET_ORDER_BY_STATUS="/users";
    String CANCEL_ORDER="/{orderId}";










}
