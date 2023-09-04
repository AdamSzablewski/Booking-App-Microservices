package com.adamszablewski.exceptions;

public class NoSuchFacilityException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Facility Not Found";

    public NoSuchFacilityException(String message) {
        super(message);
    }
    public NoSuchFacilityException() {
        super(DEFAULT_MESSAGE);
    }
}
