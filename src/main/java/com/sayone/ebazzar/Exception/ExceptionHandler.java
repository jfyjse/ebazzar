package com.sayone.ebazzar.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {RequestException.class})
    public ResponseEntity<Object> handleRequestException(RequestException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Exception exception = new Exception(e.getMessage(),
                e.getCause(),
                httpStatus,
                ZonedDateTime.now().now(ZoneId.of("Z")));
        return new ResponseEntity<>(exception,httpStatus);
    }


}
