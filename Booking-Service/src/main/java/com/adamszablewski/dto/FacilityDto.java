package com.adamszablewski.dto;

import com.adamszablewski.model.ImageData;
import lombok.*;

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
