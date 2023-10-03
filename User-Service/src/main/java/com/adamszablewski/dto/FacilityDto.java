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
public class FacilityDto {

    private Long id;
    private String name;
    private String country;
    private String region;
    private String city;
    private String street;
    private String houseNumber;
    private long owner;
    private Set<Long> employees;
    private Set<TaskDto> tasks;
}
