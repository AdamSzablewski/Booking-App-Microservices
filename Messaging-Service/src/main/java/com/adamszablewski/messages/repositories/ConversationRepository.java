package com.adamszablewski.messages.repositories;


import com.adamszablewski.messages.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Optional<Conversation> findByParticipantsContains(String user);
}
