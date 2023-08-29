package com.adamszablewski.timeSlots;

import com.adamszablewski.feignClients.classes.Employee;
import com.adamszablewski.tasks.Task;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Task task;
    private LocalDate date;
    @ManyToOne
    private Employee employee;
    private LocalTime start;
    private LocalTime end;


}
