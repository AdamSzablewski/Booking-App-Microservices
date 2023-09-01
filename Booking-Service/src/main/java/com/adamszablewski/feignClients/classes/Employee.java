package com.adamszablewski.feignClients.classes;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.tasks.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table
public class Employee extends UserClass {

    @ManyToOne
    private Facility workplace;
    private LocalTime startTime;
    private LocalTime endTime;
    @ManyToMany
    @JoinTable(
            name = "employee_tasks",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private List<Task> services;
    @OneToMany
    private List<Appointment> appointments;




}
