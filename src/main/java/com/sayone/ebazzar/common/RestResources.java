package com.sayone.ebazzar.common;

public interface RestResources {


    /*
     * API Endpoints for user related operations
     * */
    String USER_ROOT="/users";
    String UPDATE_USER_DETAILS = "/update";
    String ADD_ADDRESS= "/add-address";
    String GET_USER_DETAILS="/profile";
    String FORGET_PASSWORD="/{email}/forgot-password";
    String RESET_PASSWORD="/{email}/resetpassword";
    String DELETE_USER="/delete";

    /*
     * API Endpoints for Review related operations
     * */ String REVIEW_ROOT = "/reviews";
    String UPDATE_RATING_BY_ID = "/update/{reviewId}";
    String GET_ALL_REVIEWS = "/all";
    String GET_RATING_FOR_PRODUCT = "/all/{pid}";
    String DELETE_REVIEW = "/delete";

    /*
     * API Endpoints for Order related operations
     * */ String ORDER_ROOT = "/orders";
    String VIEW_ORDERS = "/all";
    String GET_ORDER_BY_ID = "/all/{orderId}";
    String UPDATE_ORDER_STATUS = "/{orderId}";
    String CANCEL_ORDER = "/{orderId}";

    /*
     * API Endpoints for Products
     * */ String GET_ALL_PRODUCTS = "/product";
    String GET_PRODUCT_BY_NAME = "/{name}";
    String ADD_PRODUCT = "";
    String UPDATE_PRODUCT = "/{id}";
    String DELETE_PRODUCT = "/{id}";
  
   /*
     * API Endpoints for Cart
     * */ String CART_ROOT = "/cart";
          String GET_ALL_CART_ITEMS = "/get/{uid}";
          String ADD_TO_CART = "/add/{productId}";
          String REMOVE_PRODUCT_FROM_CART = "/remove/{pid}";
  
  /*
     * API Endpoints for SubCategory
     * */
    String GET_ALL_SUBCATEGORY = "/subCategory";
    String GET_PRODUCTS_BY_SUBCATEGORY = "/{name}";

    /*
     * API Endpoints for Category
     * */
    String GET_ALL_CATEGORY = "/category";



}
