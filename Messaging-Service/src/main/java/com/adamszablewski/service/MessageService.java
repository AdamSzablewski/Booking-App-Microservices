package com.adamszablewski.service;

import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.exceptions.NotAuthorizedException;
import com.adamszablewski.feign.ImageServiceClient;
import com.adamszablewski.feign.SecurityServiceClient;
import com.adamszablewski.feign.UserServiceClient;
import com.adamszablewski.model.Conversation;
import com.adamszablewski.model.Message;
import com.adamszablewski.repository.ConversationRepository;
import com.adamszablewski.repository.MessageRepository;
import com.adamszablewski.util.ConversationCreator;
import com.adamszablewski.util.UniqueIdGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@Service
public class MessageService {
   private final MessageRepository messageRepository;
   private final ConversationRepository conversationRepository;
   private final ConversationCreator conversationCreator;
   private final UserServiceClient userServiceClient;
   private final SecurityServiceClient securityServiceClient;
   private final UniqueIdGenerator uniqueIdGenerator;
   private final ImageServiceClient imageServiceClient;

    public void addMessageToConversation(Message message) {
        messageRepository.save(message);

        Conversation conversation = conversationRepository.findByOwnerIdAndIsSystemConversation(message.getRecipientId(), true)
                .orElseGet(() -> conversationCreator.createConversation(message.getRecipientId(), true));

        conversation.getMessages().add(message);
        conversationRepository.save(conversation);

    }



    public void sendMessageToUserById(long recipientId, long senderId, String userEmail, Message message) {
        RestResponseDTO<Boolean> response = securityServiceClient.isUser(senderId, userEmail);
        if (response.getValue() == null ){
            throw new NotAuthorizedException();
        }
        Conversation ownerConversation = conversationRepository.findByOwnerIdAndRecipientId(senderId, recipientId)
                .orElseGet(()-> conversationCreator.createConversation(senderId, recipientId));
        Conversation recipientConversation = conversationRepository.findByOwnerIdAndRecipientId(recipientId, senderId)
                .orElseGet(()-> conversationCreator.createConversation(recipientId, senderId));

        String instanceID = uniqueIdGenerator.generateUniqueId();

        Message m1 = Message.builder()
                .message(message.getMessage())
                .dateSent(message.getDateSent())
                .instanceId(instanceID)
                .build();
        Message m2 = Message.builder()
                .message(message.getMessage())
                .dateSent(message.getDateSent())
                .instanceId(instanceID)
                .build();
        messageRepository.saveAll(Set.of(m1, m2));
        ownerConversation.getMessages().add(m1);
        recipientConversation.getMessages().add(m2);
        conversationRepository.saveAll(Set.of(ownerConversation, recipientConversation));

    }
    public void sendImageToUserById(long recipientId, long senderId, String userEmail, MultipartFile image) {
        RestResponseDTO<Boolean> response = securityServiceClient.isUser(senderId, userEmail);
        if (response.getValue() == null ){
            throw new NotAuthorizedException();
        }
        Conversation ownerConversation = conversationRepository.findByOwnerIdAndRecipientId(senderId, recipientId)
                .orElseGet(()-> conversationCreator.createConversation(senderId, recipientId));
        Conversation recipientConversation = conversationRepository.findByOwnerIdAndRecipientId(recipientId, senderId)
                .orElseGet(()-> conversationCreator.createConversation(recipientId, senderId));

        String instanceID = uniqueIdGenerator.generateUniqueId();
        String imageId = uniqueIdGenerator.generateUniqueImageId();

        Message m1 = Message.builder()
                .dateSent(LocalDateTime.now())
                .imageId(imageId)
                .instanceId(instanceID)
                .build();
        Message m2 = Message.builder()
                .dateSent(LocalDateTime.now())
                .imageId(imageId)
                .instanceId(instanceID)
                .build();
        messageRepository.saveAll(Set.of(m1, m2));
        ownerConversation.getMessages().add(m1);
        recipientConversation.getMessages().add(m2);
        conversationRepository.saveAll(Set.of(ownerConversation, recipientConversation));

        imageServiceClient.sendImageToImageService(image, imageId, Set.of(senderId, recipientId));

    }


}
