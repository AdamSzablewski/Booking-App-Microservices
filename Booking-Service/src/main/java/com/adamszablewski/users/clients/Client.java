package com.adamszablewski.users.clients;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.users.UserClass;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client extends UserClass {

    private int points;
    private String country;
    private String region;
    private String city;
    @OneToMany
    private List<Appointment> appointments;



}
