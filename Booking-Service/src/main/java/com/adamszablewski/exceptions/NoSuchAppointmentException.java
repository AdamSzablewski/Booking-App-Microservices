package com.adamszablewski.exceptions;

public class NoSuchAppointmentException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Appointment Not Found";

    public NoSuchAppointmentException(String message) {
        super(message);
    }
    public NoSuchAppointmentException() {
        super(DEFAULT_MESSAGE);
    }
}
