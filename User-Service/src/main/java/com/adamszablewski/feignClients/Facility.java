package com.adamszablewski.feignClients;


import com.adamszablewski.Identifiable;
import com.adamszablewski.users.employee.Employee;
import com.adamszablewski.users.owners.Owner;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Facility implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String country;
    private String region;
    private String city;
    private String street;
    private String houseNumber;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Task> tasks;

    @OneToOne(cascade = CascadeType.ALL)
    private Owner owner;
    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Employee> employees;
    @Override
    public Long getId() {
        return id;
    }
}
