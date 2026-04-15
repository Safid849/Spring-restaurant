package com.spring.restaurant.springrestaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException e) {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
    public ResponseEntity<List<String>> handleMissingParams(org.springframework.web.bind.MissingServletRequestParameterException ex) {
        List<String> errors = new ArrayList<>();

        errors.add("The parameter '" + ex.getParameterName() + "' is missing.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
