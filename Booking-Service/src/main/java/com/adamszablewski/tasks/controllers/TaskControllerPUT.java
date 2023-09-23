package com.adamszablewski.tasks.controllers;

import com.adamszablewski.dto.RestResponseDTO;
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
public class TaskControllerPUT {

    private final TaskService taskService;

    @PutMapping("/id/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Task>> changeTaskById(@PathVariable long id,
                                                                @RequestBody Task task){
        taskService.changeTask(id, task);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }


    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }



}
