package com.adamszablewski.exceptions;

public class FacilityNameTakenException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Appointment Not Found";

    public FacilityNameTakenException(String message) {
        super(message);
    }
    public FacilityNameTakenException() {
        super(DEFAULT_MESSAGE);
    }
}
