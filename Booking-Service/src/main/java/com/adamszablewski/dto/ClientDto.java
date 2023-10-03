package com.adamszablewski.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ClientDto {

    private long id;
    private int points;
    private long user;
    private Set<AppoinmentDTO> appointments;
}
