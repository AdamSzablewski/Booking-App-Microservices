package com.adamszablewski.users.clients.controllers;

import com.adamszablewski.dtos.RestResponseDTO;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.users.clients.Client;
import com.adamszablewski.users.clients.service.ClientService;
import com.adamszablewski.users.owners.Owner;
import com.adamszablewski.users.owners.service.OwnerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
@RequestMapping("/users/clients")
public class ClientControllerGET {

    private final ClientService clientService;

//    @GetMapping("/email/{email}")
//    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
//    @RateLimiter(name = "userServiceRateLimiter")
//    public ResponseEntity<RestResponseDTO<Client>> getClientByMail(@PathVariable String email){
//        RestResponseDTO<Client> responseDTO = RestResponseDTO.<Client>builder()
//                .value(clientService.getClientByMail(email))
//                .build();
//        return ResponseEntity.ok(responseDTO);
//    }
    @GetMapping("/id/{id}")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "userServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Client>> getClientById(@PathVariable long id){
        RestResponseDTO<Client> responseDTO = RestResponseDTO.<Client>builder()
                .value(clientService.getClientById(id))
                .build();
        return ResponseEntity.ok(responseDTO);
    }
    @PutMapping("/create/id/{id}")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "userServiceRateLimiter")
    ResponseEntity<RestResponseDTO<String>> makeEmployeeFromUser(@PathVariable long id){
        clientService.makeClientFromUser(id);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }

    public ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }

}
