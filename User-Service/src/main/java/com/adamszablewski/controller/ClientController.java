package com.adamszablewski.controller;

import com.adamszablewski.dto.ClientDto;
import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.service.ClientService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
@RequestMapping("/users/clients")
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/id/{id}")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "userServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<ClientDto>> getClientById(@PathVariable long id){
        RestResponseDTO<ClientDto> responseDTO = RestResponseDTO.<ClientDto>builder()
                .value(clientService.getClientById(id))
                .build();
        return ResponseEntity.ok(responseDTO);
    }
    @PutMapping("/create/id/{id}")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "userServiceRateLimiter")
    ResponseEntity<RestResponseDTO<String>> makeClientFromUserId(@PathVariable long id){
        clientService.makeClientFromUser(id);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }

    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }

}
