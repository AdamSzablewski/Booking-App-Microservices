package com.adamszablewski.facilities.controller;

import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.facilities.service.FacilityService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/facilities/create")
public class FacilityControllerPOST {

    private final FacilityService facilityService;

    @PostMapping("/")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    ResponseEntity<RestResponseDTO<String>> createFacility(@RequestBody Facility facility,
                                                           @RequestHeader("userEmail") String ownerEmail){
        System.out.println("user in post facility "+ownerEmail);
        facilityService.createFacility(facility, ownerEmail);
        return ResponseEntity.ok(new RestResponseDTO<>());
  }

    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }
}
