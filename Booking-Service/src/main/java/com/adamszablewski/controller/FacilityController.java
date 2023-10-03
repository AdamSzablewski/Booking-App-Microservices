package com.adamszablewski.controller;

import com.adamszablewski.dto.FacilityDto;
import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.model.Facility;
import com.adamszablewski.service.FacilityService;
import com.adamszablewski.model.Task;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/facilities")
public class FacilityController {

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
    @PostMapping("/")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    ResponseEntity<RestResponseDTO<String>> createFacility(@RequestBody Facility facility,
                                                           @RequestHeader("userEmail") String ownerEmail){
        facilityService.createFacility(facility, ownerEmail);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    //todo add put


    @PatchMapping("id/{id}/add/services/")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<String>> addServiceToFacility(@PathVariable Long id, @RequestBody Task service,
                                                                         @RequestHeader("userEmail") String userEmail){
        facilityService.addTaskToFacility(id, service, userEmail);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    @PatchMapping("/add/employee/{email}/facility/id/{facilityId}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<String>> addEmployeeToFacility(@PathVariable String email,
                                                                         @PathVariable long facilityId,
                                                                         @RequestHeader("userEmail") String userEmail){
        facilityService.addEmployeeToFacility(email, facilityId, userEmail);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }

    @DeleteMapping("/id/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    ResponseEntity<RestResponseDTO<String>> removeFacilityById(@PathVariable Long id, @RequestHeader("userEmail") String userEmail ){
        facilityService.removeFacilityById(id, userEmail);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }

    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }
}
