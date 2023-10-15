package com.adamszablewski.exceptions;


import com.adamszablewski.dto.RestResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomExceptionHandler {
    public static ResponseEntity<RestResponseDTO<?>> handleException(Throwable ex) {
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

        } else if (ex instanceof ClientAlreadyCreatedException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);

        }  else if (ex instanceof EmployeeAlreadyCreatedException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);

        } else if (ex instanceof InvalidCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);

        }  else if (ex instanceof OwnerAlreadyCreatedException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);

        } else if (ex instanceof UsernameTakenException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);

        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }
}
