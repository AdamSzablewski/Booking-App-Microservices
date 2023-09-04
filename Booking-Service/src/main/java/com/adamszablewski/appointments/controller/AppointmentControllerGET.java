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
    public ResponseEntity<RestResponseDTO<?>> getAllAppointments(){
        RestResponseDTO<?> appointmentDTO = buildListResponse(appointmentService.getAllAppointments());
        return ResponseEntity.ok(appointmentDTO);
    }
    @GetMapping("/id/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<?>> getAppointmentById(@PathVariable Long id){
        RestResponseDTO<?> restResponseDTO = buildResponse(appointmentService.getAppointmentById(id));
        return ResponseEntity.ok(restResponseDTO);

    }
    @GetMapping("/number/{number}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<?>> getAppointmentByPhoneNumber(@PathVariable String number){
        RestResponseDTO<?> appointmentDTO = buildResponse(appointmentService.getAppointmentByPhoneNumber(number));
        return ResponseEntity.ok(appointmentDTO);
    }
    @GetMapping("/email/{email}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<?>> getAppointmentByEmail(@PathVariable String email){
        RestResponseDTO<?> appointmentDTO = buildResponse(appointmentService.getAppointmentByEmail(email));
        return ResponseEntity.ok(appointmentDTO);
    }
    public  ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable, new RestResponseDTO<>());
    }
    private <T> RestResponseDTO<T> buildListResponse(List<T> dataList){
        return RestResponseDTO.<T>builder()
                .values(dataList)
                .build();
    }
    private <T> RestResponseDTO<T> buildResponse(T data){
        return RestResponseDTO.<T>builder()
                .value(data)
                .build();
    }
}
