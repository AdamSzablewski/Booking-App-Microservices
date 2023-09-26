package com.adamszablewski.users;


import com.adamszablewski.Identifiable;
import com.adamszablewski.feignClients.Appointment;
import com.adamszablewski.users.clients.Client;
import com.adamszablewski.users.employee.Employee;
import com.adamszablewski.users.owners.Owner;
import com.adamszablewski.users.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
