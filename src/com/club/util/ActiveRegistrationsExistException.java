package com.club.util;

public class ActiveRegistrationsExistException extends Exception {
    public ActiveRegistrationsExistException() {
        super();
    }

    public ActiveRegistrationsExistException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Cannot delete member. " + getMessage();
    }
}
