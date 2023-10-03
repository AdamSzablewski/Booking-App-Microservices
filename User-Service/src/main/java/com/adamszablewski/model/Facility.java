package com.adamszablewski.model;


import com.adamszablewski.Identifiable;
import jakarta.persistence.*;
import lombok.*;


import java.util.Iterator;
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

    @OneToMany
    private Set<Task> tasks;
    @OneToOne
    private Owner owner;
    @OneToMany
    @ToString.Exclude
    private Set<Employee> employees;

    @Override
    public Long getId() {
        return id;
    }
}
