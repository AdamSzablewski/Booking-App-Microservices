package com.adamszablewski.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppoinmentDTO {

    private long id;
    private long facility;
    private TaskDto task;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String number;
    private String email;
    private long client;
    private EmployeeDto employee;

}
