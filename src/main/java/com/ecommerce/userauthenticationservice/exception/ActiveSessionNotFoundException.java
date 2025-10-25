package com.ecommerce.userauthenticationservice.exception;

public class ActiveSessionNotFoundException extends RuntimeException {
    public ActiveSessionNotFoundException(String message) {
        super(message);
    }
}
