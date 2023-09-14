package com.adamszablewski.tasks;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.feignClients.classes.Employee;
import com.adamszablewski.timeSlots.TimeSlot;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String city;
    private String region;
    private String category;
    private String name;
    @OneToMany
    private List<Appointment> appointments;
    @ManyToOne
    private Facility facility;
    @ManyToMany
    private List<Employee> employees;
    private double price;
    private int durationInMinutes;
    private String currency;
}
