package com.adamszablewski.controller;

import com.adamszablewski.dto.OwnerDto;
import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.service.OwnerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
@RequestMapping("/users/owner")
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping("/email/{email}")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "userServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<OwnerDto>> getOwnerByEmail(@PathVariable String email){
        RestResponseDTO<OwnerDto> responseDTO = RestResponseDTO.<OwnerDto>builder()
                .value(ownerService.getOwnerByEmail(email))
                .build();
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/id/{id}")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "userServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<OwnerDto>> getOwnerById(@PathVariable long id){
        RestResponseDTO<OwnerDto> responseDTO = RestResponseDTO.<OwnerDto>builder()
                .value(ownerService.getOwnerById(id))
                .build();
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/id/{id}")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "userServiceRateLimiter")
    ResponseEntity<RestResponseDTO<String>> makeOwnerFromUser(@PathVariable long id){
        ownerService.makeOwnerFromUser(id);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }

    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }


}
