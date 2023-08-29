package com.adamszablewski.messages.service;

import com.adamszablewski.exceptions.NoSuchUserFoundException;
import com.adamszablewski.messages.Conversation;
import com.adamszablewski.messages.helpers.ConversationCreator;
import com.adamszablewski.messages.repositories.ConversationRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final ConversationCreator conversationCreator;

    public Optional<Conversation> getCoversation(String user) {
        Optional<Conversation> optionalConversation = conversationRepository.findByParticipantsContains(user);
        if (optionalConversation.isEmpty()){
            throw new NoSuchUserFoundException();
        }
        return optionalConversation;

    }

    public ResponseEntity<String> createConversation(String user) {
        conversationCreator.createConversation(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
//    public Conversation createConversation(String user) {
//        Optional<Conversation> optionalConversation =  conversationRepository.findByParticipantsContains(user);
//        if (optionalConversation.isPresent()){
//            return optionalConversation.get();
//        }
//        Conversation conversation = Conversation.builder()
//                .participants(List.of(user, "support"))
//                .messages(new ArrayList<>())
//                .build();
//        conversationRepository.save(conversation);
//
//        return conversation;
//    }


}
