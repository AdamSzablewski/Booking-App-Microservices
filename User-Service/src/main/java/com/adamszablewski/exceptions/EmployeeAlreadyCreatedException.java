package com.adamszablewski.exceptions;

public class EmployeeAlreadyCreatedException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "The user already has status of employee";

    public EmployeeAlreadyCreatedException(String message) {
        super(message);
    }
    public EmployeeAlreadyCreatedException() {
        super(DEFAULT_MESSAGE);
    }
}
