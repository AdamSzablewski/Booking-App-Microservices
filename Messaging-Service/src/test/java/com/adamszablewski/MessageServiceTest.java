package com.adamszablewski;

import com.adamszablewski.model.Conversation;
import com.adamszablewski.model.Message;
import com.adamszablewski.model.UserClass;
import com.adamszablewski.repository.ConversationRepository;
import com.adamszablewski.repository.MessageRepository;
import com.adamszablewski.service.MessageService;
import com.adamszablewski.util.ConversationCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase
@DataJpaTest(properties = "spring.config.name=application-test")
public class MessageServiceTest {

    private MessageService messageService;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private ConversationRepository conversationRepository;
    @Mock
    private ConversationCreator conversationCreator;

    @BeforeEach
    void setup(){
        messageService = new MessageService(messageRepository, conversationRepository, conversationCreator);
    }

    @Test
    void addMessageToConversationTest_should_add_to_Existing_conversation_for_1_User(){
        UserClass user = UserClass.builder()
                .id(1L)
                .build();

        Message message = Message.builder()
                .id(1L)
                .message("Test message")
                .dateSent(LocalDateTime.now())
                .sender("Booking App")
                .receivers(List.of(user.getId()))
                .build();
        Conversation conversation = Conversation.builder()
                .id(1L)
                .user(user)
                .messages(new ArrayList<>())
                .build();

        when(conversationRepository.findByUserId(message.getReceivers().get(0))).thenReturn(Optional.of(conversation));


        messageService.addMessageToConversation(message);

        assertThat(conversation.getMessages().size()).isEqualTo(1);
    }
    @Test
    void addMessageToConversationTest_should_create_conversation_for_1_User(){
        UserClass user = UserClass.builder()
                .id(1L)
                .build();

        Message message = Message.builder()
                .id(1L)
                .message("Test message")
                .dateSent(LocalDateTime.now())
                .sender("Booking App")
                .receivers(List.of(user.getId()))
                .build();
        Conversation conversation = Conversation.builder()
                .id(1L)
                .user(user)
                .messages(new ArrayList<>())
                .build();

        when(conversationRepository.findByUserId(message.getReceivers().get(0))).thenReturn(Optional.empty());
        when(conversationCreator.createConversation(user.getId())).thenReturn(conversation);


        messageService.addMessageToConversation(message);

        assertThat(conversation.getMessages().size()).isEqualTo(1);
    }
    @Test
    void addMessageToConversationTest_should_add_to_Existing_conversation_for_2_Users(){

        Message message = Message.builder()
                .id(1L)
                .message("Test message")
                .dateSent(LocalDateTime.now())
                .sender("Booking App")
                .receivers(List.of(1L, 2L))
                .build();
        Conversation conversation = Conversation.builder()
                .id(1L)
                .messages(new ArrayList<>())
                .build();
        Conversation conversation2 = Conversation.builder()
                .id(1L)
                .messages(new ArrayList<>(Arrays.asList(
                        Message.builder()
                                .id(0L)
                                .message("Old message")
                                .build()))
                )
                .build();

        when(conversationRepository.findByUserId(message.getReceivers().get(0))).thenReturn(Optional.of(conversation));
        when(conversationRepository.findByUserId(message.getReceivers().get(1))).thenReturn(Optional.of(conversation2));


        messageService.addMessageToConversation(message);

        assertThat(conversation.getMessages().size()).isEqualTo(1);
        assertThat(conversation2.getMessages().size()).isEqualTo(2);

    }
}
