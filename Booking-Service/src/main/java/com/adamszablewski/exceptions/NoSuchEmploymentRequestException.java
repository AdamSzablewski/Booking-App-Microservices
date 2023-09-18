package com.adamszablewski.exceptions;

public class NoSuchEmploymentRequestException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Employment request Not Found";

    public NoSuchEmploymentRequestException(String message) {
        super(message);
    }
    public NoSuchEmploymentRequestException() {
        super(DEFAULT_MESSAGE);
    }
}
