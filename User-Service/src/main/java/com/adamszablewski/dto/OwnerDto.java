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
public class OwnerDto {

    private long id;
    private Set<FacilityDto> facilities;
    private Long user;

}