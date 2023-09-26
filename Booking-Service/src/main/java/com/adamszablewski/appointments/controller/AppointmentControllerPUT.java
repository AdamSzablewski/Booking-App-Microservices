package com.adamszablewski.appointments.controller;

import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.appointments.service.AppointmentService;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/appointments/change")
public class AppointmentControllerPUT {
    private final AppointmentService appointmentService;

    @PutMapping("/id/{id}/employee/email/{email}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<String>> changeEmployeeForAppointmentById(@PathVariable Long id){
        appointmentService.changeEmployeeForAppointmentById(id);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    @PutMapping("/close/id/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<String>> markAppointmentAsDone(@PathVariable Long id){
        appointmentService.markAppointmentAsDone(id);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    public  ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }
}
