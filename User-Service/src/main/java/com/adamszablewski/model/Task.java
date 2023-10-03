package com.adamszablewski.model;


import com.adamszablewski.Identifiable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
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
    private String category;
    private String name;

    @OneToMany
    @ToString.Exclude
    private Set<Appointment> appointments;

    @ManyToOne
    @ToString.Exclude
    private Facility facility;
    @ManyToMany
    @ToString.Exclude
    private Set<Employee> employees;
    private double price;
    private int durationInMinutes;
    private String currency;

    @PreRemove
    @Transactional
    private void preRemove(){
        Iterator<Employee> iterator = employees.iterator();
        while(iterator.hasNext()){
            Employee employee = iterator.next();
            employee.getTasks().remove(this);
        }

        facility.getTasks().remove(this);
    }
    @Override
    public Long getId() {
        return id;
    }
}