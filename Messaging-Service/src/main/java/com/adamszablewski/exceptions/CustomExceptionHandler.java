package com.adamszablewski.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomExceptionHandler {
    public static ResponseEntity<String> handleException(Throwable ex) {
        // Handle the exception and create a meaningful error response for the user
        if (ex instanceof NoSuchUserFoundException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
//        } else if (ex instanceof NoSuchOrderException) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } else {
            // Handle other exceptions or provide a generic error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong. Please try again later.");
        }
    }
}
