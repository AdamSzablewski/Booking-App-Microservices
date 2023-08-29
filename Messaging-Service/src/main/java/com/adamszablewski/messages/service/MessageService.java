package com.adamszablewski.messages.service;

import com.adamszablewski.messages.Message;
import com.adamszablewski.messages.helpers.MessageSender;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MessageService {
   private final MessageSender messageSender;

    public ResponseEntity<Message> createMessageByCustomer(Message message, String user) {
        Message newMessagge =  messageSender.createMessage(message, user, false);

        return ResponseEntity.status(HttpStatus.CREATED).body(newMessagge);
    }

    public ResponseEntity<Message> createMessageBySupport(Message message, String user) {
        Message newMessagge =  messageSender.createMessage(message, user, true);

        return ResponseEntity.status(HttpStatus.CREATED).body(newMessagge);
    }

    public void addMessageToConversation(Message message) {
      messageSender.addMessageToConversation(message);
    }
}
