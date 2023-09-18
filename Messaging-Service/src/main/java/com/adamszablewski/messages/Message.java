package com.adamszablewski.messages;

import com.adamszablewski.classes.UserClass;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany
    @JsonIgnore
    private List<Conversation> conversations;

    private String message;

    private String sender;
    @ManyToMany
    @JoinTable(
            name = "message_receiver",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserClass> receivers;

    private LocalDateTime dateSent;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", sender='" + sender + '\'' +
                ", receivers=" + receivers +
                ", dateSent=" + dateSent +
                '}';
    }
}
