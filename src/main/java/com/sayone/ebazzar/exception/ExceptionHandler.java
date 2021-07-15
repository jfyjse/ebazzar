package com.sayone.ebazzar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = RequestException.class)
    public ResponseEntity<Object> handleApiRequestException(RequestException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Exception exception = new Exception(e.getMessage(),
                e.getCause(),
                httpStatus,
                ZonedDateTime.now().now(ZoneId.of("Z")));
        return new ResponseEntity<>(exception,httpStatus);
    }
}
