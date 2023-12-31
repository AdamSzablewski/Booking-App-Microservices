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
public class TaskDto {


    private long id;
    private String city;
    private String region;
    private String country;
    private String category;
    private String name;
    private double price;
    private int durationInMinutes;
    private String currency;
    private long facility;
    private Set<Long> employees;
    private Set<Long> appointments;
}
