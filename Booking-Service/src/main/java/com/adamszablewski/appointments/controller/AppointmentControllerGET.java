package com.adamszablewski.appointments.controller;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.dto.AppoinmentDTO;
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
@RequestMapping("/appointments")
public class AppointmentControllerGET {
    private final AppointmentService appointmentService;

    @GetMapping("/")
    @ResponseBody
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<AppoinmentDTO>> getAllAppointments(){
        RestResponseDTO<AppoinmentDTO> appointmentDTO = RestResponseDTO.<AppoinmentDTO>builder()
                .values(appointmentService.getAllAppointments())
                .build();
        return ResponseEntity.ok(appointmentDTO);
    }
    @GetMapping("/id/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<AppoinmentDTO>> getAppointmentById(@PathVariable Long id){
        RestResponseDTO<AppoinmentDTO> restResponseDTO = RestResponseDTO.<AppoinmentDTO>builder()
                .value(appointmentService.getAppointmentById(id))
                .build();
        return ResponseEntity.ok(restResponseDTO);

    }
    public  ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }
}
