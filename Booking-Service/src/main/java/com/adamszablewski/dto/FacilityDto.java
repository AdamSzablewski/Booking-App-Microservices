package com.adamszablewski.dto;

import lombok.*;

import java.util.List;

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
    private List<Long> employees;
    private List<TaskDto> tasks;
}
