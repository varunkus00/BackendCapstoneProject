package com.ecommerce.userauthenticationservice.exception;

public class AuthorizationTokenNotFound extends RuntimeException{
    public AuthorizationTokenNotFound(String message) {
        super(message);
    }
}
