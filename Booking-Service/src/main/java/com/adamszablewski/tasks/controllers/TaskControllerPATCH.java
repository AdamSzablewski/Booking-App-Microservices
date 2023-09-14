package com.adamszablewski.tasks.controllers;

import com.adamszablewski.appointments.dtos.RestResponseDTO;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.tasks.Task;
import com.adamszablewski.tasks.services.TaskService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/services/change")
@AllArgsConstructor
public class TaskControllerPATCH {

    private final TaskService taskService;

    @PatchMapping("/remove/employee/id/{id}/task/id/{taskId}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Task>> removeEmployeeFromTask (@PathVariable long id,
                                                                         @PathVariable long taskId){
        taskService.removeEmployeeFromTask(id, taskId);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    @PatchMapping("/add/employee/id/{id}/task/id/{taskId}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Task>> addEmployeeTOTask (@PathVariable long id,
                                                                         @PathVariable long taskId){
        taskService.addEmployeeTOTask(id, taskId);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }


    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }



}
