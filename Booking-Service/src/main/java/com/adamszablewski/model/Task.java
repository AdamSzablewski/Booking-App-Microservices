package com.adamszablewski.model;

import com.adamszablewski.model.Appointment;
import com.adamszablewski.model.Facility;
import com.adamszablewski.model.Employee;

import com.adamszablewski.util.Identifiable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String city;
    private String region;
    @ManyToOne
    private Category category;
    private String name;

    @OneToMany(cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private Set<Appointment> appointments;

    @ManyToOne
    @ToString.Exclude
    private Facility facility;
    @ManyToMany(cascade = CascadeType.DETACH)
    @ToString.Exclude
    private Set<Employee> employees;
    private double price;
    private int durationInMinutes;
    private String currency;
    private int points;

    @Override
    public Long getId() {
        return id;
    }
    public void addPoints(){
        this.points = this.points + 1;
    }
    public void addPoints(int i){
        this.points = this.points + i;
    }
}