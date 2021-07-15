package com.sayone.ebazzar.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<Object> handleCustomExceptions(CustomException ex, WebRequest request)
    {
        ExpRepresentation exp = new ExpRepresentation(new Date(),ex.getMessage());
        return new ResponseEntity<>(exp,new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request)
    {
        ExpRepresentation exp = new ExpRepresentation(new Date(),ex.getMessage());
        return new ResponseEntity<>(exp,new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
