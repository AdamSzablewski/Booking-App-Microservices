package com.adamszablewski.dto;


import com.adamszablewski.classes.Identifiable;
import com.adamszablewski.classes.UserClass;
import com.adamszablewski.messages.Message;
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
public class ConversationDTO {

    private long id;
    private UserClass user;
    private List<MessageDTO> messages;
    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", userId=" + user +
                '}';
    }
}
