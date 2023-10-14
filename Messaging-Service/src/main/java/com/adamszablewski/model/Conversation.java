package com.adamszablewski.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Conversation implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @OneToOne
    private UserClass user;
    @ManyToMany()
    @JoinTable(
            name = "conversation_messages",
            joinColumns = @JoinColumn(name = "conversation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "message_id", referencedColumnName = "id")
    )
    @JsonIgnoreProperties("conversation")
    private List<Message> messages;
    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", userId=" + user +
                '}';
    }
    @Override
    public Long getId() {
        return id;
    }
}
