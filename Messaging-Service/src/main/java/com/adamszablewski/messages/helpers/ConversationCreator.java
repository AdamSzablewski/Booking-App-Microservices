package com.adamszablewski.messages.helpers;

import com.adamszablewski.classes.UserClass;
import com.adamszablewski.messages.Conversation;
import com.adamszablewski.messages.repositories.ConversationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Component
public class ConversationCreator {

    private final ConversationRepository conversationRepository;

    public Conversation createConversation(UserClass user) {

//        Optional<Conversation> optionalConversation =  conversationRepository.findByParticipantsContains(userId);
//        if (optionalConversation.isPresent()){
//            return optionalConversation.get();
//        }
        Conversation conversation = Conversation.builder()
                .user(user)
                .messages(new ArrayList<>())
                .build();
        conversationRepository.save(conversation);

        return conversation;
    }
}
