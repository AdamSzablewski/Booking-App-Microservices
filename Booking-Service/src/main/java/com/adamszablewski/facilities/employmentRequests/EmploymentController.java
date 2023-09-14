package com.adamszablewski.facilities.employmentRequests;

import com.adamszablewski.appointments.dtos.RestResponseDTO;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.facilities.Facility;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/facilities/employment")
@AllArgsConstructor
public class EmploymentController {
    private final EmploymentRequestService employmentRequestService;

    @GetMapping("/request/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Facility>> acceptEmploymentRequest(@PathVariable long id,
                                                                             @RequestBody boolean status){
        RestResponseDTO<Facility> responseDTO = RestResponseDTO.<Facility>builder()
                .values(employmentRequestService.acceptEmploymentRequest(id, status))
                .build();
        return ResponseEntity.ok(responseDTO);
    }
    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }
}
