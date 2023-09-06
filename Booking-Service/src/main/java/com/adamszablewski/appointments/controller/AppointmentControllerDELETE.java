package com.adamszablewski.appointments.controller;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.dtos.RestResponseDTO;
import com.adamszablewski.appointments.service.AppointmentService;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/appointments/cancel")
public class AppointmentControllerDELETE {
    private final AppointmentService appointmentService;

    @DeleteMapping("/id/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<String>> deleteAppointmentById(@PathVariable Long id){
        appointmentService.deleteAppointmentById(id);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }

    public  ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }

}
