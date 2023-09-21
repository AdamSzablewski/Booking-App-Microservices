package com.adamszablewski.messages.helpers;


import com.adamszablewski.messages.Conversation;
import com.adamszablewski.messages.Message;
import com.adamszablewski.messages.repositories.ConversationRepository;
import com.adamszablewski.messages.repositories.MessageRepository;
import com.adamszablewski.messages.service.ConversationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class MessageSender {

    private final ConversationRepository conversationRepository;
    private final ConversationService conversationService;
    private final MessageRepository messageRepository;
    private final ConversationCreator conversationCreator;




//    public void addMessageToConversation(Message message, Conversation conversation){
//        List<Message> messages = conversation.getMessages();
//        messages.add(message);
//        messageRepository.save(message);
//    }
    @Transactional
    public void addMessageToConversation(Message message){
        messageRepository.save(message);
        message.getReceivers().forEach(reciever -> {
            Optional<Conversation> optionalConversation = conversationRepository.findByUserId(reciever.getId());
            Conversation conversation;
            if (optionalConversation.isEmpty()){
                conversation = conversationCreator.createConversation(reciever);
            }else {
                conversation = optionalConversation.get();
            }
            System.out.println(conversation);

            conversation.getMessages().add(message);
            if (message.getConversations() == null){
                message.setConversations(new ArrayList<>());
            }
             message.getConversations().add(conversation);
            conversationRepository.save(conversation);
        });

        System.out.println("printing from addmessagetoconv "+ message);


    }


}
