package com.adamszablewski.dto;

import lombok.*;

import java.util.List;
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
    private List<Long> employees;
}
