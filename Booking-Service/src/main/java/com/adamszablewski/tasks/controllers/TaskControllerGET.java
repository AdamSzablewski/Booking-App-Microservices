package com.adamszablewski.tasks.controllers;

import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.dto.TaskDto;
import com.adamszablewski.exceptions.CustomExceptionHandler;
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
    public ResponseEntity<RestResponseDTO<TaskDto>> getAllServicesFOrFacilityByName(@PathVariable String name){
        RestResponseDTO<TaskDto> responseDTO = RestResponseDTO.<TaskDto>builder()
                .values(serviceService.getAllTasksForFacilityByName(name))
                .build();
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/facility/id/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<TaskDto>> getAllTasksForFacilityById(@PathVariable Long id){
        RestResponseDTO<TaskDto> responseDTO = RestResponseDTO.<TaskDto>builder()
                .values(serviceService.getAllTasksforFacilityById(id))
                .build();
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/id/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<TaskDto>> getServiceById(@PathVariable Long id){
        RestResponseDTO<TaskDto> responseDTO = RestResponseDTO.<TaskDto>builder()
                .value(serviceService.getServiceById(id))
                .build();
        return ResponseEntity.ok(responseDTO);

    }
    @GetMapping("/city/{city}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<TaskDto>> getTasksForCity( @PathVariable String city){
        RestResponseDTO<TaskDto> responseDTO = RestResponseDTO.<TaskDto>builder()
                .values(serviceService.getTasksForCity(city))
                .build();
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/city/{city}/category/{category}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<TaskDto>> getTasksForCityByCategory(@PathVariable String city,
                                                @PathVariable String category){
        RestResponseDTO<TaskDto> responseDTO = RestResponseDTO.<TaskDto>builder()
                .values(serviceService.getTasksForCityByCategory(city, category))
                .build();
        return ResponseEntity.ok(responseDTO);
    }



    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }



}
