package com.adamszablewski.model;


import com.adamszablewski.Identifiable;
import com.adamszablewski.model.Client;
import com.adamszablewski.model.Employee;
import com.adamszablewski.model.Owner;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToOne(cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private Owner owner;
    @OneToOne(cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private Employee employee;
    @OneToOne(cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private Client client;
    @JsonIgnore
    private String password;

    @Override
    public Long getId() {
        return id;
    }
}
