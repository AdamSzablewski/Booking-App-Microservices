package com.adamszablewski.messages.helpers;

import com.adamszablewski.classes.UserClass;
import com.adamszablewski.classes.UserRepository;
import com.adamszablewski.exceptions.NoSuchUserFoundException;
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
    private final UserRepository userRepository;

    public Conversation createConversation(long id) {

//        Optional<Conversation> optionalConversation =  conversationRepository.findByParticipantsContains(userId);
//        if (optionalConversation.isPresent()){
//            return optionalConversation.get();
//        }
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
