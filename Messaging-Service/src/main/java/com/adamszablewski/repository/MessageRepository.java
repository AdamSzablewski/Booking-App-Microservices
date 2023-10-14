package com.adamszablewski.repository;


import com.adamszablewski.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

//    Optional<Message> findBySender(String sender);
//    Optional<Message> findByReceiver(String sender);
}
