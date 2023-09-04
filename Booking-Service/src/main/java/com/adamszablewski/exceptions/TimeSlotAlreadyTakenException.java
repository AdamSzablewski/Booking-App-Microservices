package com.adamszablewski.exceptions;

public class TimeSlotAlreadyTakenException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "The time slot is already taken.";

    public TimeSlotAlreadyTakenException(String message) {
        super(message);
    }
    public TimeSlotAlreadyTakenException() {
        super(DEFAULT_MESSAGE);
    }
}
