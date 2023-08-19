package com.adamszablewski.users.clients;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client extends User {
    private int points;
    private String country;
    private String region;
    private String city;



}
