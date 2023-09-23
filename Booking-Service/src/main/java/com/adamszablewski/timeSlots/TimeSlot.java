package com.adamszablewski.timeSlots;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
//    @ManyToOne
//    private Task task;
    private TaskDto task;
    private LocalDate date;
//    @OneToOne
//    private Employee employee;
    private EmployeeDto emloyee;
    //@ManyToOne
   // private Facility facility;
    private FacilityDto facility;
    private LocalTime startTime;
    private LocalTime endTime;

}
