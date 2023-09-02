package com.adamszablewski.messages.controllers.conversationControllers;


import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.messages.Conversation;
import com.adamszablewski.messages.service.ConversationService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/conversations")
public class ConversationControllerPOST {

    private final ConversationService conversationService;
//    @CircuitBreaker(name = "messageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
//    @RateLimiter(name = "messageServiceRateLimiter")
//    @PostMapping("/conversations/create/customer/{customer}")
//    //@PreAuthorize("hasAuthority('ADMIN') or principal.username == #customer")
//    public ResponseEntity<String> createConversation(@PathVariable String customer){
//        return conversationService.createConversation(customer);
//    }

    private ResponseEntity<String> fallBackMethod(String customer, Throwable ex){
        return CustomExceptionHandler.handleException(ex);
    }
}
