package com.adamszablewski.service;

import com.adamszablewski.model.Conversation;
import com.adamszablewski.model.Message;
import com.adamszablewski.repository.ConversationRepository;
import com.adamszablewski.repository.MessageRepository;
import com.adamszablewski.util.ConversationCreator;
import com.adamszablewski.util.MessageSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class MessageService {
   private final MessageRepository messageRepository;
   private final ConversationRepository conversationRepository;
   private final ConversationCreator conversationCreator;

    public void addMessageToConversation(Message message) {
        messageRepository.save(message);
        message.getReceivers().forEach(reciever -> {
            Optional<Conversation> optionalConversation = conversationRepository.findByUserId(reciever);
            Conversation conversation;
            if (optionalConversation.isEmpty()){
                conversation = conversationCreator.createConversation(reciever);
            }else {
                conversation = optionalConversation.get();
            }
            conversation.getMessages().add(message);
            conversationRepository.save(conversation);
        });
    }
}
