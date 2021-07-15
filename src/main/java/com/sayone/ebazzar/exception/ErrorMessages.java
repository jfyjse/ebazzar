package com.sayone.ebazzar.exception;

public enum ErrorMessages {
    MISSING_REQUIRED_FIELD("MISSING REQUIRED FIELD"),
    CART_ALREADY_CHECKED_OUT("record already exists.Please debug."),
    OUT_OF_STOCK("required quantity not available. Please update Cart Quantity/ Please wait for the seller to add products"),
    INTERNAL_SERVER_ERROR("internal error.Please debug."),
    NO_RECORD_FOUND("no record found.Please debug."),
    NO_ORDER_FOUND("There are no orders for this user"),
    AUTHENTICATION_FAILED("authentication failed.Please debug."),
    COULD_NOT_UPDATE_RECORD("could not update record.Please debug."),
    COULD_NOT_DELETE_RECORD("could not delete record.Please debug."),
    EMAIL_NOT_VERIFIED("email could not be verified.Please debug.");

    private String errorMessage;

    ErrorMessages(String errorMessage){
        this.errorMessage=errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
