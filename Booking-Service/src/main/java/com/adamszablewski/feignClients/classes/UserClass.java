package com.adamszablewski.feignClients.classes;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.util.Identifiable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Builder
public class UserClass implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String country;
    private String region;
    private String city;
    private String email;
    private String phoneNumber;
    @OneToOne(cascade = CascadeType.ALL)
    private Employee employee;
    @OneToOne(cascade = CascadeType.ALL)
    private Owner owner;
    @OneToOne(cascade = CascadeType.ALL)
    private Client client;
    @JsonIgnore
    private String password;

    @Override
    public Long getId() {
        return id;
    }
}