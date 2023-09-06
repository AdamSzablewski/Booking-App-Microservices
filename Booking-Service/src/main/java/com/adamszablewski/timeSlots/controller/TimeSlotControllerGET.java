package com.adamszablewski.timeSlots.controller;

import com.adamszablewski.appointments.dtos.RestResponseDTO;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.timeSlots.TimeSlot;
import com.adamszablewski.timeSlots.service.TimeSlotService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/timeslots")
public class TimeSlotControllerGET {

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


    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }
}
