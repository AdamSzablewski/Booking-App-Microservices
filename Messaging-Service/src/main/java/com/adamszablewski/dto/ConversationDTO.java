package com.adamszablewski.dto;



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
public class ConversationDTO {

    private long id;
    private long user;
    private List<MessageDTO> messages;
    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", userId=" + user +
                '}';
    }
}
