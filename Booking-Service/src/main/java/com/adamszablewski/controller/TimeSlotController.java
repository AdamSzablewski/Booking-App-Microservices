package com.adamszablewski.controller;

import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.model.TimeSlot;
import com.adamszablewski.service.TimeSlotService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
@RequestMapping("/timeslots")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    @GetMapping("/task/id/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<TimeSlot>> getAvailableTimeSlotsForTaskAndDate
            (@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
             @PathVariable Long id){

        RestResponseDTO<TimeSlot> responseDTO = RestResponseDTO.<TimeSlot>builder()
                .values(timeSlotService.getAvailableTimeSlotsForTaskAndDate(date, id))
                .build();
        return ResponseEntity.ok(responseDTO);
    }
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
