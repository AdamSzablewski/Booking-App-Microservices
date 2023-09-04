package com.adamszablewski.exceptions;

import com.adamszablewski.appointments.dtos.RestResponseDTO;
import com.adamszablewski.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomExceptionHandler <T> {
    public static ResponseEntity<RestResponseDTO<?>> handleException(Throwable ex, RestResponseDTO<?> response) {
        RestResponseDTO<?> responseBody = RestResponseDTO.builder()
                .error(ex.getMessage())
                .build();
        if (ex instanceof NoSuchAppointmentException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } else if (ex instanceof NoSuchFacilityException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } else if (ex instanceof NoSuchTaskException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        } else if (ex instanceof NoSuchUserException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } else if (ex instanceof TimeSlotAlreadyTakenException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }
}
