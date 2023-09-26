package com.adamszablewski.appointments;

import com.adamszablewski.facilities.Facility;
import com.adamszablewski.feignClients.classes.Client;
import com.adamszablewski.feignClients.classes.Employee;
import com.adamszablewski.tasks.Task;
import com.adamszablewski.util.Identifiable;
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

    @ManyToOne
    @ToString.Exclude
    private Client client;

    @ManyToOne
    @ToString.Exclude
    private Employee employee;

    @Override
    public Long getId() {
        return id;
    }

}
