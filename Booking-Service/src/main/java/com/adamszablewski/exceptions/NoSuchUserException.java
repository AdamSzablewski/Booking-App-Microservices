package com.adamszablewski.exceptions;

public class NoSuchUserException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "User Not Found";

    public NoSuchUserException(String message) {
        super(message);
    }
    public NoSuchUserException() {
        super(DEFAULT_MESSAGE);
    }
}
