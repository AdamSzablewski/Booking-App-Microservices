package com.adamszablewski.dto;


import com.adamszablewski.Identifiable;
import com.adamszablewski.feignClients.Facility;
import com.adamszablewski.users.UserClass;
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
public class OwnerDto {

    private long id;
    private List<FacilityDto> facilities;
    private Long user;

}