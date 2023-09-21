package com.adamszablewski.feignClients;


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
public class Facility {

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
    @JsonIgnoreProperties("facility")
    private List<Task> tasks;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("facilities")
    private Owner owner;
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("workplace")
    @ToString.Exclude
    private List<Employee> employees;
}
