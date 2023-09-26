package com.adamszablewski.messages.controllers.conversationControllers;



import com.adamszablewski.RestResponseDTO;
import com.adamszablewski.dto.ConversationDTO;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.messages.Conversation;
import com.adamszablewski.messages.repositories.ConversationRepository;
import com.adamszablewski.messages.service.ConversationService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/conversations")
public class ConversationControllerGET {

    private final ConversationService conversationService;
    private final ConversationRepository conversationRepository;

    @GetMapping("/user/{id}")
    @CircuitBreaker(name = "messagingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "messagingServiceRateLimiter")
    //@PreAuthorize("hasAuthority('ADMIN') or principal.username == #user")
    public ResponseEntity<RestResponseDTO<ConversationDTO>> getCoversation(@PathVariable long id){
        RestResponseDTO<ConversationDTO> responseDTO = RestResponseDTO.<ConversationDTO>builder()
                .value(conversationService.getCoversation(id))
                .build();
        return ResponseEntity.ok(responseDTO);
    }
//    @CircuitBreaker(name = "messageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
//    @RateLimiter(name = "messageServiceRateLimiter")
    @GetMapping("/all")
    @CircuitBreaker(name = "messagingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "messagingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Conversation>> getAll(){
        RestResponseDTO<Conversation> responseDTO = RestResponseDTO.<Conversation>builder()
                .values(conversationRepository.findAll())
                .build();
        return ResponseEntity.ok(responseDTO);
    }
    public  ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }
   
}
