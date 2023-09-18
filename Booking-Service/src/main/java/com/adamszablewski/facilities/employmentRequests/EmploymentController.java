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

    @PutMapping("/request/{id}")
    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "bookingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Facility>> answereEmploymentRequest(@PathVariable long id,
                                                                             @RequestParam("status") boolean status){
        employmentRequestService.answereEmploymentRequest(id, status);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }
}
