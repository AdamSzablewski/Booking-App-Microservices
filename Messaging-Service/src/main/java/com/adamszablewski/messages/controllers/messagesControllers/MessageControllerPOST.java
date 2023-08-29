package com.adamszablewski.messages.controllers.messagesControllers;



import com.adamszablewski.messages.Message;
import com.adamszablewski.messages.service.MessageService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/messages")
public class MessageControllerPOST {

    MessageService messageService;

    @PostMapping("/user/{user}")
    //@PreAuthorize("principal.username == #user")
    public ResponseEntity<Message> createConversationByVendor(@RequestBody Message message, @PathVariable String user){
        return messageService.createMessageByCustomer(message, user);
    }

    @PostMapping("/support/{user}")
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    public ResponseEntity<Message> createConversationBySupport(@RequestBody Message message, @PathVariable String user){
        return messageService.createMessageBySupport(message, user);
    }
}
