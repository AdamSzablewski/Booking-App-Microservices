package com.adamszablewski.repository;


import com.adamszablewski.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    //Optional<Conversation> findByParticipantsContains(String user);

    Optional<Conversation> findByUserId(Long reciever);
}
