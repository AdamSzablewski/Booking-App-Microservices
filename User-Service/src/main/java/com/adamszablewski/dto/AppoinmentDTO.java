package com.adamszablewski.dto;


import com.adamszablewski.users.clients.Client;
import com.adamszablewski.users.employee.Employee;
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
    private FacilityDto facility;
    private TaskDto task;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String number;
    private String email;
    private ClientDto client;
    private EmployeeDto employee;

}
