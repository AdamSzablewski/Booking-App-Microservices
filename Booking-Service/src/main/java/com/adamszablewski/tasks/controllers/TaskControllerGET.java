package com.adamszablewski.tasks.controllers;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.dtos.RestResponseDTO;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.tasks.Task;
import com.adamszablewski.tasks.services.TaskService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/services")
@AllArgsConstructor
public class TaskControllerGET {

    private final TaskService serviceService;

    @GetMapping("/facility/name/{name}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Task>> getAllServicesFOrFacilityByName(@PathVariable String name){
        RestResponseDTO<Task> responseDTO = RestResponseDTO.<Task>builder()
                .values(serviceService.getAllTasksForFacilityByName(name))
                .build();
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/facility/id/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Task>> getAllTasksForFacilityById(@PathVariable Long id){
        RestResponseDTO<Task> responseDTO = RestResponseDTO.<Task>builder()
                .values(serviceService.getAllTasksforFacilityById(id))
                .build();
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/id/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Task>> getServiceById(@PathVariable Long id){
        RestResponseDTO<Task> responseDTO = RestResponseDTO.<Task>builder()
                .value(serviceService.getServiceById(id))
                .build();
        return ResponseEntity.ok(responseDTO);

    }
    @GetMapping("/city/{city}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Task>> getTasksForCity( @PathVariable String city){
        RestResponseDTO<Task> responseDTO = RestResponseDTO.<Task>builder()
                .values(serviceService.getTasksForCity(city))
                .build();
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/city/{city}/category/{category}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Task>> getTasksForCityByCategory(@PathVariable String city,
                                                @PathVariable String category){
        RestResponseDTO<Task> responseDTO = RestResponseDTO.<Task>builder()
                .values(serviceService.getTasksForCityByCategory(city, category))
                .build();
        return ResponseEntity.ok(responseDTO);
    }



    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }



}
