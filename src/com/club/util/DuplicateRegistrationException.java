package com.club.util;

public class DuplicateRegistrationException extends Exception {
    public DuplicateRegistrationException() {
        super();
    }

    public DuplicateRegistrationException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Duplicate Registration: " + getMessage();
    }
}
