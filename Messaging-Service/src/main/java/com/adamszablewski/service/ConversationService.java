package com.adamszablewski.service;

import com.adamszablewski.dto.ConversationDTO;
import com.adamszablewski.exceptions.NoSuchConversationFoundException;
import com.adamszablewski.exceptions.NoSuchUserFoundException;
import com.adamszablewski.model.Conversation;
import com.adamszablewski.util.ConversationCreator;
import com.adamszablewski.repository.ConversationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

//import static com.adamszablewski.util.Mapper.mapConversationToDTO;

@AllArgsConstructor
@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;


    public Conversation getCoversation(long user) {
        return conversationRepository.findByUserId(user)
                .orElseThrow(NoSuchConversationFoundException::new);
//                mapConversationToDTO(conversationRepository.findByUserId(user)
//                .orElseThrow(NoSuchUserFoundException::new));

    }

    public void deleteConversation(long id) {
        Conversation conversation = conversationRepository.findByUserId(id)
                .orElseThrow(NoSuchConversationFoundException::new);
        conversationRepository.delete(conversation);
    }

//    public ResponseEntity<String> createConversation(String user) {
//        conversationCreator.createConversation(user);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//
//    }
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
