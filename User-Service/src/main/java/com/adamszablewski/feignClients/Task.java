package com.adamszablewski.feignClients;


import com.adamszablewski.users.employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String city;
    private String region;
    private String category;
    private String name;
    @OneToMany
    private List<Appointment> appointments;
    @ManyToOne
    private Facility facility;
    @ManyToMany
    @JoinTable(
            name = "appointment_employee",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employees;
    private double price;
    private int durationInMinutes;
    private String currency;
}
