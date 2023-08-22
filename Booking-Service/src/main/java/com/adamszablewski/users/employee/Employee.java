package com.adamszablewski.users.employee;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.tasks.Task;
import com.adamszablewski.timeSlots.TimeSlot;
import com.adamszablewski.users.UserClass;
import com.adamszablewski.users.owners.Owner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
