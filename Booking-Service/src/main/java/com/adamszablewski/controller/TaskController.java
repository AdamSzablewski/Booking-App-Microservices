package com.adamszablewski.controller;

import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.dto.TaskDto;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.model.Task;
import com.adamszablewski.service.TaskService;
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
public class TaskController {

    private final TaskService serviceService;
    private final TaskService taskService;

    @GetMapping("/facility/name/{name}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<TaskDto>> getAllServicesForFacilityByName(@PathVariable String name){
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
    @PostMapping("/facility/id/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Task>> createTaskForFacility(@PathVariable long id,
                                                                       @RequestBody Task task,
                                                                       @RequestHeader("userEmail") String userEmail){
        taskService.createTaskForFacility(id, task, userEmail);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    @PutMapping("/id/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Task>> changeTaskById(@PathVariable long id,
                                                                @RequestBody Task task,
                                                                @RequestHeader("userEmail") String userEmail){
        taskService.changeTask(id, task, userEmail);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }


    @PatchMapping("/remove/employee/id/{id}/task/id/{taskId}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Task>> removeEmployeeFromTask (@PathVariable long id,
                                                                         @PathVariable long taskId,
                                                                         @RequestHeader("userEmail") String userEmail){
        taskService.removeEmployeeFromTask(id, taskId, userEmail);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    @PatchMapping("/add/employee/id/{id}/task/id/{taskId}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Task>> addEmployeeTOTask (@PathVariable long id,
                                                                    @PathVariable long taskId,
                                                                    @RequestHeader("userEmail") String userEmail){
        taskService.addEmployeeTOTask(id, taskId, userEmail);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }

    @DeleteMapping("/id/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Task>> deleteTaskById(@PathVariable long id,
                                                                @RequestHeader("userEmail") String userEmail){
        serviceService.deleteTaskById(id, userEmail);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }

    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }



}
