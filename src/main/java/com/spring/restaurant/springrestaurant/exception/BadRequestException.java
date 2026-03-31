package com.spring.restaurant.springrestaurant.exception;

public class BadRequestException extends Exception {
    public BadRequestException(String message) {
        super(message);
    }
}
