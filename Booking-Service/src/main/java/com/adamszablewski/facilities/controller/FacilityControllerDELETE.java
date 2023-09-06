package com.adamszablewski.facilities.controller;

import com.adamszablewski.appointments.dtos.RestResponseDTO;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.facilities.service.FacilityService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/facilities")
public class FacilityControllerDELETE {

    private final FacilityService facilityService;

    @DeleteMapping("/id/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    ResponseEntity<RestResponseDTO<String>> removeFacilityById(@RequestParam Long id){
        facilityService.removeFacilityById(id);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }

    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }

}
