package com.adamszablewski.feignClients;


import com.adamszablewski.Identifiable;
import com.adamszablewski.users.clients.Client;
import com.adamszablewski.users.employee.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigInteger;
import java.time.LocalDate;
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
    //@JsonIgnoreProperties({"appointment","appointments"})
    private Facility facility;
    @ManyToOne
    //@JsonIgnoreProperties({"appointment","appointments"})
    @JsonBackReference
    @ToString.Exclude
    private Task task;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String number;
    private String email;
    @OneToOne
    @JsonBackReference
    //@JsonIgnoreProperties({"appointment","appointments"})
    @ToString.Exclude
    private Client client;
    @OneToOne
    @JsonBackReference
    //@JsonIgnoreProperties({"appointment","appointments"})
    @ToString.Exclude
    private Employee employee;

    @Override
    public Long getId() {
        return id;
    }

}