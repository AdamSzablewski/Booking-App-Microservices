package com.adamszablewski.exceptions;

public class FIleNotFoundException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "Failed to establish a connection with the remote service";

    public FIleNotFoundException(String message) {
        super(message);
    }
    public FIleNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
