package com.adamszablewski.exceptions;

public class NoSuchCategoryException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "This category doesn't exist";

    public NoSuchCategoryException(String message) {
        super(message);
    }
    public NoSuchCategoryException() {
        super(DEFAULT_MESSAGE);
    }
}
