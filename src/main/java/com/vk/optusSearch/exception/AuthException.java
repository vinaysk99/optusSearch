package com.vk.optusSearch.exception;

public class AuthException extends RuntimeException {

    private String message;

    public AuthException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
