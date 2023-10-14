package com.adamszablewski.dto;


import com.adamszablewski.model.UserClass;
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
