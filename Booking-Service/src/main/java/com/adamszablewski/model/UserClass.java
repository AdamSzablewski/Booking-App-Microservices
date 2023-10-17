package com.adamszablewski.model;

import com.adamszablewski.util.Identifiable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
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
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @ToString.Exclude
    private Employee employee;
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @ToString.Exclude
    private Owner owner;
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @ToString.Exclude
    private Client client;
    @JsonIgnore
    private String password;

    @Override
    public Long getId() {
        return id;
    }
}