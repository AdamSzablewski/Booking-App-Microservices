package com.adamszablewski.facilities;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.feignClients.classes.Owner;
import com.adamszablewski.tasks.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
    private String region;
    private String city;
    private String street;
    private String houseNumber;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Task> tasks;
    @JsonIgnore
    @OneToMany
    private List<Appointment> appointments;
    @ManyToOne
    @JsonIgnore
    private Owner owner;




}
