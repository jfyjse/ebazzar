package com.sayone.ebazzar.exception;

public enum ErrorMessages {

    MISSING_REQUIRED_FIELD("Missing Required field. Please check documentation for required fields"),

    RECORD_ALREADY_EXISTS("Record already exists"),

    NO_RECORD_FOUND("Record with provided id is not found"),

    COULD_NOT_DELETE_RECORD("Could not delete record"),

    INTERNAL_SERVER_ERROR("Internal server error"),


    COULD_NOT_UPDATE_RECORD("Could not update record");

    private String errorMessages;


    ErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }

    public String getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }
}
