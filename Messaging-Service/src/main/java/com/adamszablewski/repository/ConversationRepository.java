package com.adamszablewski.repository;


import com.adamszablewski.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    //Optional<Conversation> findByParticipantsContains(String user);

    Optional<Conversation> findByUserId(Long reciever);


    boolean existsByParticipantsContainingAllIds(Set<Long> recipients);
    Optional<Conversation> findByParticipantsContainingAllIds(Set<Long> ids);
    Optional<Conversation> findByOwnerIdAndRecipientId(long ownerId, long recipientId);
    Optional<Conversation> findByOwnerIdAndIsSystemConversation(long ownerId, boolean isSystemConversation);
}
