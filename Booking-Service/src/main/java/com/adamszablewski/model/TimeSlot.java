package com.adamszablewski.model;

import com.adamszablewski.dto.EmployeeDto;
import com.adamszablewski.dto.FacilityDto;
import com.adamszablewski.dto.TaskDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private TaskDto task;
    private LocalDate date;
    private EmployeeDto emloyee;
    private FacilityDto facility;
    private LocalTime startTime;
    private LocalTime endTime;

}
