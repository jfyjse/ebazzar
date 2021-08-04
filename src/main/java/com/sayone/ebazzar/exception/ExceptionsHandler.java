package com.sayone.ebazzar.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = {RequestException.class})
    public ResponseEntity<Object> handleRequestException(RequestException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Exception exception = new Exception(e.getMessage(), httpStatus, ZonedDateTime.now().now(ZoneId.of("Z")));
        return new ResponseEntity<>(exception, httpStatus);
    }

    @ExceptionHandler(value = {java.lang.Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
        ExpRepresentation exp = new ExpRepresentation(new Date(), ex.getMessage());
        return new ResponseEntity<>(exp, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
