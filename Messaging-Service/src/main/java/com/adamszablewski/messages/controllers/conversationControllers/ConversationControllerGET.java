package com.adamszablewski.messages.controllers.conversationControllers;



import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.messages.Conversation;
import com.adamszablewski.messages.repositories.ConversationRepository;
import com.adamszablewski.messages.service.ConversationService;

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
    private ConversationRepository conversationRepository;
//    @CircuitBreaker(name = "messageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
//    @RateLimiter(name = "messageServiceRateLimiter")
    @GetMapping("/user/{user}")
    //@PreAuthorize("hasAuthority('ADMIN') or principal.username == #user")
    public Conversation getCoversation(@PathVariable long user){
        return conversationService.getCoversation(user);
    }
//    @CircuitBreaker(name = "messageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
//    @RateLimiter(name = "messageServiceRateLimiter")
    @GetMapping("/all")
    public List<Conversation> getAll(){
        return conversationRepository.findAll();
    }
    private ResponseEntity<String> fallBackMethod(String customer, Throwable ex){
        return CustomExceptionHandler.handleException(ex);
    }
   
}
