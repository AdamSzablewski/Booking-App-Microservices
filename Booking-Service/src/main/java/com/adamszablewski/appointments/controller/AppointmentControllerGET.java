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

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/appointments")
public class AppointmentControllerGET {
    private final AppointmentService appointmentService;

    @GetMapping("/")
    @ResponseBody
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Appointment>> getAllAppointments(){
        RestResponseDTO<Appointment> appointmentDTO = RestResponseDTO.<Appointment>builder()
                .values(appointmentService.getAllAppointments())
                .build();
        return ResponseEntity.ok(appointmentDTO);
    }
    @GetMapping("/id/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Appointment>> getAppointmentById(@PathVariable Long id){
        RestResponseDTO<Appointment> restResponseDTO = RestResponseDTO.<Appointment>builder()
                .value(appointmentService.getAppointmentById(id))
                .build();
        return ResponseEntity.ok(restResponseDTO);

    }
    @GetMapping("/number/{number}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Appointment>> getAppointmentByPhoneNumber(@PathVariable String number){
        RestResponseDTO<Appointment> appointmentDTO =RestResponseDTO.<Appointment>builder()
                .value(appointmentService.getAppointmentByPhoneNumber(number))
                .build();
        return ResponseEntity.ok(appointmentDTO);
    }

    public  ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }
}
