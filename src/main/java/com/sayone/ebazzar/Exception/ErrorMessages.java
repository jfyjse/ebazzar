package com.sayone.ebazzar.Exception;

public enum ErrorMessages {

    MISSING_REQURED_FIELD("Missing Required field. Please check documentation for required fields");

    ErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }

    private String errorMessages;

    public String getErrorMessages() {
        return errorMessages;
    }
}

