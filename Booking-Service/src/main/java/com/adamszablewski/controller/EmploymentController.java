package com.adamszablewski.controller;

import com.adamszablewski.dto.EmploymentRequestDTO;
import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.service.EmploymentRequestService;
import com.adamszablewski.exceptions.CustomExceptionHandler;
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

    @PutMapping("/request/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<String>> answereEmploymentRequest(@PathVariable long id,
                                                                             @RequestParam("status") boolean status,
                                                                            @RequestHeader("userEmail") String userEmail){
        employmentRequestService.answereEmploymentRequest(id, status, userEmail);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    @GetMapping("/request/user/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<EmploymentRequestDTO>> getEmploymentRequestsForUser(@PathVariable long id,
                                                                                              @RequestHeader("userEmail") String userEmail){
        RestResponseDTO<EmploymentRequestDTO> responseDTO = RestResponseDTO.<EmploymentRequestDTO>builder()
                .values(employmentRequestService.getEmploymentRequestsForUser(id, userEmail))
                .build();
        return ResponseEntity.ok(responseDTO);
    }
    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }
}
