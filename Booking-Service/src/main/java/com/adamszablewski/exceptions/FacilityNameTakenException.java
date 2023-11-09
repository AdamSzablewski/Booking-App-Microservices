package com.adamszablewski.exceptions;

public class FacilityNameTakenException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Facility name already taken";

    public FacilityNameTakenException(String message) {
        super(message);
    }
    public FacilityNameTakenException() {
        super(DEFAULT_MESSAGE);
    }
}
