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
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class MessageSender {

    private final ConversationRepository conversationRepository;
    private final ConversationService conversationService;
    private final MessageRepository messageRepository;
    private final ConversationCreator conversationCreator;




    public void addMessageToConversation(Message message, Conversation conversation){
        List<Message> messages = conversation.getMessages();
        messages.add(message);
        messageRepository.save(message);
    }

    public void addMessageToConversation(Message message){
        Optional<Conversation> optionalConversation =  conversationRepository.findByParticipantsContains(message.getSender());
        Conversation conversation = null;
        if (optionalConversation.isEmpty()){

            conversation = conversationCreator.createConversation(getUsernameFromMessage(message));

        }else{
            conversation = optionalConversation.get();
        }
        List<Message> messages = conversation.getMessages();
        messages.add(message);
        messageRepository.save(message);
        conversationRepository.save(conversation);
    }
    private String getUsernameFromMessage(Message message){
        if (message.getSender().equalsIgnoreCase("support"))return message.getReceiver();
        else return message.getSender();
    }


    public Message createMessage(Message message, String user, boolean isSupport) {
        Optional<Conversation> optionalConversation =  conversationRepository.findByParticipantsContains(user);
        Conversation conversation;
        if (optionalConversation.isEmpty()){
            conversation =  conversationCreator.createConversation(user);
        }else {
            conversation = optionalConversation.get();
        }
        String sender = isSupport ? "support" : user;
        String receiver = isSupport ? user : "support";

        Message newMessage = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .message(message.getMessage())
                .conversation(conversation)
                .dateSent(LocalDateTime.now())
                .build();
        addMessageToConversation(newMessage);

        return newMessage;
    }

}
