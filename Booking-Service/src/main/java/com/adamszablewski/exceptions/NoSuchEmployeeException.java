package com.adamszablewski.exceptions;

public class NoSuchEmployeeException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Employee Not Found";

    public NoSuchEmployeeException(String message) {
        super(message);
    }
    public NoSuchEmployeeException() {
        super(DEFAULT_MESSAGE);
    }
}
