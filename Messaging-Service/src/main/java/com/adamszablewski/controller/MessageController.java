package com.adamszablewski.controller;

import com.adamszablewski.dto.MessageDTO;
import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.model.Message;
import com.adamszablewski.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
@RequestMapping("/messages")
public class MessageController {
    MessageService messageService;

    @PostMapping("/user/{recipientId}/from/{senderId}")
    public ResponseEntity<RestResponseDTO<MessageDTO>> sendMessageToUserById(@PathVariable long recipientId,
                                                                          @PathVariable long senderId,
                                                                          @RequestHeader("userEmail") String userEmail,
                                                                          @RequestBody Message message){
        messageService.sendMessageToUserById(recipientId, senderId, userEmail, message);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    @PostMapping("/user/{recipientId}/from/{senderId}/image")
    public ResponseEntity<RestResponseDTO<MessageDTO>> sendImageToUserById(@PathVariable long recipientId,
                                                                             @PathVariable long senderId,
                                                                             @RequestHeader("userEmail") String userEmail,
                                                                             @RequestParam MultipartFile image){
        messageService.sendImageToUserById(recipientId, senderId, userEmail, image);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
}
