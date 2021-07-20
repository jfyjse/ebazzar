package com.sayone.ebazzar.Exception;

public class RequestException extends RuntimeException{
    public RequestException(String message){
        super(message);
    }
    public RequestException(String message, Throwable cause){
        super(message,cause);
    }
}
