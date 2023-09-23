package com.adamszablewski.appointments;

import com.adamszablewski.facilities.Facility;
import com.adamszablewski.feignClients.classes.Client;
import com.adamszablewski.feignClients.classes.Employee;
import com.adamszablewski.tasks.Task;

import com.adamszablewski.util.Identifiable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Appointment implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    private Facility facility;

    @ManyToOne
    @ToString.Exclude
    private Task task;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String number;

    private String email;

    @OneToOne
    @ToString.Exclude
    private Client client;

    @OneToOne
    @ToString.Exclude
    private Employee employee;

    @Override
    public Long getId() {
        return id;
    }

}
