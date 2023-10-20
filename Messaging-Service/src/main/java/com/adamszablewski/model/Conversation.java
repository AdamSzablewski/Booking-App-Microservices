package com.adamszablewski.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Conversation implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private long ownerId;
    private long recipientId;
    @OneToMany(fetch = FetchType.EAGER)
    @OrderBy("dateSent DESC")
    @ManyToOne
    private List<Message> messages;
    private boolean isSystemConversation;

    @Override
    public Long getId() {
        return id;
    }
}
