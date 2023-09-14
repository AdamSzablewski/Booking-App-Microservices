package com.adamszablewski.users.clients;

import com.adamszablewski.feignClients.Appointment;
import com.adamszablewski.users.UserClass;
import com.adamszablewski.users.employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;
    private int points;
    private String country;
    private String region;
    private String city;
    @OneToOne
    private UserClass userClass;
    @OneToMany
    private List<Appointment> appointments;



}
