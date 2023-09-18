package com.adamszablewski.facilities.controller;

import com.adamszablewski.appointments.dtos.RestResponseDTO;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.facilities.service.FacilityService;
import com.adamszablewski.tasks.Task;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Controller
@AllArgsConstructor
@RequestMapping("/facilities/change")
public class FacilityControllerPATCH {

    private final FacilityService facilityService;

    @PatchMapping("id/{id}/add/services/")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<String>> addServiceToFacility(@PathVariable Long id, @RequestBody Task service){
        facilityService.addServiceToFacility(id, service);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    @PatchMapping("/add/employee/{email}/facility/id/{facilityId}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<String>> addEmployeeToFacility(@PathVariable String email,
                                                                         @PathVariable long facilityId){
        facilityService.addEmployeeToFacility(email, facilityId);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }

}
