package com.adamszablewski.tasks;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.feignClients.classes.Employee;
import com.adamszablewski.timeSlots.TimeSlot;

import com.adamszablewski.util.Identifiable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String city;
    private String region;
    private String category;
    private String name;
    @OneToMany
    //@JsonIgnoreProperties({"task", "tasks"})
    @JsonManagedReference
    @ToString.Exclude
    private List<Appointment> appointments;
    @ManyToOne
    //@JsonIgnoreProperties({"task", "tasks"})
    @JsonBackReference
    @ToString.Exclude
    private Facility facility;
    @ManyToMany
    //@JsonIgnoreProperties({"task", "tasks"})
    //@JsonIgnore
    @JsonManagedReference
    @ToString.Exclude
    private List<Employee> employees;
    private double price;
    private int durationInMinutes;
    private String currency;
    @Override
    public Long getId() {
        return id;
    }
}