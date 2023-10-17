package com.adamszablewski.util;


import com.adamszablewski.exceptions.NoSuchUserFoundException;
import com.adamszablewski.model.Conversation;
import com.adamszablewski.repository.ConversationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;

@AllArgsConstructor
@Component
public class ConversationCreator {



    private final ConversationRepository conversationRepository;


    public Conversation createConversation(long id) {
        Conversation conversation = Conversation.builder()
                .userId(id)
                .messages(new HashSet<>())
                .build();
        conversationRepository.save(conversation);

        return conversation;
    }
}
