package com.adamszablewski.util;

import com.adamszablewski.model.UserClass;
import com.adamszablewski.repository.UserRepository;
import com.adamszablewski.exceptions.NoSuchUserFoundException;
import com.adamszablewski.model.Conversation;
import com.adamszablewski.repository.ConversationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@AllArgsConstructor
@Component
public class ConversationCreator {



    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public Conversation createConversation(long id) {

        UserClass user = userRepository.findById(id)
                .orElseThrow(NoSuchUserFoundException::new);

        Conversation conversation = Conversation.builder()
                .user(user)
                .messages(new ArrayList<>())
                .build();
        conversationRepository.save(conversation);

        return conversation;
    }
}
