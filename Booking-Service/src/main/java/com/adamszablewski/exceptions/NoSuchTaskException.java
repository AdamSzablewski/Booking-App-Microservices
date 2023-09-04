package com.adamszablewski.exceptions;

public class NoSuchTaskException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Task Not Found";

    public NoSuchTaskException(String message) {
        super(message);
    }
    public NoSuchTaskException() {
        super(DEFAULT_MESSAGE);
    }
}
