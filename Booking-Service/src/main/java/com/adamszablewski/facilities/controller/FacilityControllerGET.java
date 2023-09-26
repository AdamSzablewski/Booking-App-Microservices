package com.adamszablewski.facilities.controller;

import com.adamszablewski.dto.FacilityDto;
import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.facilities.service.FacilityService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/facilities")
public class FacilityControllerGET {

    private final FacilityService facilityService;

    @GetMapping("/")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<FacilityDto>> getAllFacilities(){
        RestResponseDTO<FacilityDto> responseDTO = RestResponseDTO.<FacilityDto>builder()
                .values(facilityService.getAllFacilities())
                .build();
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/region/{region}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<FacilityDto>> getAllFacilitiesForRegion(@PathVariable String region){
        RestResponseDTO<FacilityDto> responseDTO = RestResponseDTO.<FacilityDto>builder()
                .values(facilityService.getAllFacilitiesForRegion(region))
                .build();
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/user/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<FacilityDto>> getFacilityForUser(@PathVariable long id){
        RestResponseDTO<FacilityDto> responseDTO = RestResponseDTO.<FacilityDto>builder()
                .value(facilityService.getFacilityForUser(id))
                .build();
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/id/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<FacilityDto>> getFacilityById(@PathVariable Long id){
        RestResponseDTO<FacilityDto> responseDTO = RestResponseDTO.<FacilityDto>builder()
                .value(facilityService.getFacilityById(id))
                .build();
        return ResponseEntity.ok(responseDTO);

    }
    @GetMapping("/city/{city}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<FacilityDto>>  getAllFacilitiesForCity(@RequestParam String city){
        RestResponseDTO<FacilityDto> responseDTO = RestResponseDTO.<FacilityDto>builder()
                .values(facilityService.getAllFacilitiesForCity(city))
                .build();
        return ResponseEntity.ok(responseDTO);
    }
    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }
}
