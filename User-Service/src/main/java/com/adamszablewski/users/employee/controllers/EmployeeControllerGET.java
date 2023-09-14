package com.adamszablewski.users.employee.controllers;

import com.adamszablewski.dtos.RestResponseDTO;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.users.employee.Employee;
import com.adamszablewski.users.employee.service.EmployeeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@AllArgsConstructor
@RequestMapping("/users/employee")
public class EmployeeControllerGET {

    private final EmployeeService employeeService;

//    @GetMapping("/email/{email}")
//    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
//    @RateLimiter(name = "userServiceRateLimiter")
//    public ResponseEntity<RestResponseDTO<Employee>> getEmployeeByMail(@PathVariable String email){
//            RestResponseDTO<Employee> responseDTO = RestResponseDTO.<Employee>builder()
//                    .value(employeeService.getEmployeeByEmail(email))
//                    .build();
//        return ResponseEntity.ok(responseDTO);
//    }

    @GetMapping("/id/{id}")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "userServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Employee>>  getEmployeeByMail(@PathVariable long id){
        RestResponseDTO<Employee> responseDTO = RestResponseDTO.<Employee>builder()
                .value(employeeService.getEmployeeById(id))
                .build();
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/com/adamszablewski/users/employee")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "userServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Employee>>  getEmployeesByTaskAndFacility(@RequestParam("taskName") String taskName,
                                                 @RequestParam("facilityName") String facilityName){
        RestResponseDTO<Employee> responseDTO = RestResponseDTO.<Employee>builder()
                .values(employeeService.getEmployeesByTaskAndFacility(taskName, facilityName))
                .build();
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/id/{id}")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "userServiceRateLimiter")
    ResponseEntity<RestResponseDTO<String>> makeEmployeeFromUser(@PathVariable long id, @RequestBody Employee employee){
        employeeService.makeEmployeeFromUser(id, employee);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }

    @PostMapping("/ids")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "userServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Employee>>  getAllEmployeesByIds(@RequestBody List<Long> ids){
        RestResponseDTO<Employee> responseDTO = RestResponseDTO.<Employee>builder()
                .values(employeeService.getAllEmployeesByIds(ids))
                .build();
        return ResponseEntity.ok(responseDTO);
    }
    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }


}
