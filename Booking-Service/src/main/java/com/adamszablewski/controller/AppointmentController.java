package com.adamszablewski.controller;

import com.adamszablewski.dto.AppoinmentDTO;
import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.service.AppointmentService;
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
public class AppointmentController {
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
