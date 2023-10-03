package com.adamszablewski.model;

import com.adamszablewski.util.Identifiable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Facility implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
    private String region;
    private String city;
    private String street;
    private String houseNumber;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Task> tasks;

    @ManyToOne
    @ToString.Exclude
    private Owner owner;
    @OneToMany
    @ToString.Exclude
    private Set<Employee> employees;
    @Override
    public Long getId() {
        return id;
    }
}
