package com.adamszablewski.dto;

import com.adamszablewski.feignClients.Appointment;
import com.adamszablewski.users.UserClass;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ClientDto {

    private long id;
    private int points;
    private long user;
    private List<AppoinmentDTO> appointments;
}
