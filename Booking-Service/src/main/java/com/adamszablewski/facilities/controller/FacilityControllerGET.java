package com.adamszablewski.facilities.controller;

import com.adamszablewski.appointments.dtos.RestResponseDTO;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.facilities.service.FacilityService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/facilities")
public class FacilityControllerGET {

    private final FacilityService facilityService;

    @GetMapping("/")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Facility>> getAllFacilities(){
        RestResponseDTO<Facility> responseDTO = RestResponseDTO.<Facility>builder()
                .values(facilityService.getAllFacilities())
                .build();
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/region/{region}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Facility>> getAllFacilitiesForRegion(@RequestParam String region){
        RestResponseDTO<Facility> responseDTO = RestResponseDTO.<Facility>builder()
                .values(facilityService.getAllFacilitiesForRegion(region))
                .build();
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/id/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Facility>> getFacilityById(@RequestParam Long id){
        RestResponseDTO<Facility> responseDTO = RestResponseDTO.<Facility>builder()
                .value(facilityService.getFacilityById(id))
                .build();
        return ResponseEntity.ok(responseDTO);

    }
    @GetMapping("/city/{city}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Facility>>  getAllFacilitiesForCity(@RequestParam String city){
        RestResponseDTO<Facility> responseDTO = RestResponseDTO.<Facility>builder()
                .values(facilityService.getAllFacilitiesForCity(city))
                .build();
        return ResponseEntity.ok(responseDTO);
    }
    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }
}
