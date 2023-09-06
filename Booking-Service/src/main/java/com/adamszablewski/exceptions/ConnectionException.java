package com.adamszablewski.exceptions;

public class ConnectionException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "Failed to establish a connection with the remote service";

    public ConnectionException(String message) {
        super(message);
    }
    public ConnectionException() {
        super(DEFAULT_MESSAGE);
    }
}
