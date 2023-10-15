package com.adamszablewski.exceptions;

import com.adamszablewski.dto.RestResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomExceptionHandler {

    public static ResponseEntity<RestResponseDTO<?>> handleException(Throwable ex) {
        ex.printStackTrace();
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
        } else if (ex instanceof FacilityNameTakenException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }
        else if (ex instanceof NotAuthorizedException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }
        else if (ex instanceof NoSuchEmploymentRequestException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }
}
