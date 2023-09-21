package com.adamszablewski.feignClients;


import com.adamszablewski.users.employee.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

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
    @JsonIgnoreProperties("task")
    @ToString.Exclude
    private List<Appointment> appointments;
    @ManyToOne
    @JsonIgnoreProperties("tasks")
    @ToString.Exclude
    private Facility facility;
    @ManyToMany
    @JsonIgnoreProperties("tasks")
    @JsonIgnore
    @ToString.Exclude
    private List<Employee> employees;
    private double price;
    private int durationInMinutes;
    private String currency;
}