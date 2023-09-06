package com.adamszablewski.timeSlots.controller;

import com.adamszablewski.appointments.dtos.RestResponseDTO;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.timeSlots.TimeSlot;
import com.adamszablewski.timeSlots.service.TimeSlotService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/timeslots")
public class TimeSlotControllerPOST {

    TimeSlotService timeSlotService;

    @PostMapping("/user/id/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<String>> makeAppointmentFromTimeSlot(@PathVariable long id, @RequestBody TimeSlot timeSlot){
        timeSlotService.makeAppointmentFromTimeSlot(id, timeSlot);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }

    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }

}
