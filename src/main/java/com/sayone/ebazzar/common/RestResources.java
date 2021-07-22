package com.sayone.ebazzar.common;

public interface RestResources {

    /*
     * API Endpoints for Review related operations
     * */
    String REVIEW_ROOT = "/reviews";
    String UPDATE_RATING_BY_ID ="/update/{reviewId}";
    String GET_RATING_PID="/{pid}";
    String DELETE_REVIEW="/delete";

    /*
     * API Endpoints for Order related operations
     * */
    String ORDER_ROOT = "/orders";
    String GET_ORDER_BY_ID = "/{id}";
    String VIEW_ORDERS="/all";
    String VIEW_ALL_ORDERS="/{userId}/all";
    String UPDATE_ORDER_STATUS="/{orderId}";
    String CANCEL_ORDER="/{orderId}";










}
