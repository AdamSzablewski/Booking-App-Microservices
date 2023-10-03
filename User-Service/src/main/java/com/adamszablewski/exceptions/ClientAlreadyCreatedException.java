package com.adamszablewski.exceptions;

public class ClientAlreadyCreatedException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "The user already has status of client";

    public ClientAlreadyCreatedException(String message) {
        super(message);
    }
    public ClientAlreadyCreatedException() {
        super(DEFAULT_MESSAGE);
    }
}
