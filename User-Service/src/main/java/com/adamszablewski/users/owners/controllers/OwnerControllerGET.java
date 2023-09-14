package com.adamszablewski.users.owners.controllers;

import com.adamszablewski.dtos.RestResponseDTO;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.users.employee.Employee;
import com.adamszablewski.users.owners.Owner;
import com.adamszablewski.users.owners.service.OwnerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@AllArgsConstructor
@RequestMapping("/users/owner")
public class OwnerControllerGET {

    OwnerService ownerService;

    @GetMapping("/email/{email}")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "userServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Owner>> getOwnerByEmail(@PathVariable String email){
        RestResponseDTO<Owner> responseDTO = RestResponseDTO.<Owner>builder()
                .value(ownerService.getOwnerByEmail(email))
                .build();
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/id/{id}")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "userServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Owner>> getOwnerById(@PathVariable long id){
        RestResponseDTO<Owner> responseDTO = RestResponseDTO.<Owner>builder()
                .value(ownerService.getOwnerById(id))
                .build();
        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }


}
