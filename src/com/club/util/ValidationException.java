package com.club.util;

public class ValidationException extends Exception {
    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Validation Error: " + getMessage();
    }
}