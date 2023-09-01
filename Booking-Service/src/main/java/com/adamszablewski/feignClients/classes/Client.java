package com.adamszablewski.feignClients.classes;

import com.adamszablewski.appointments.Appointment;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class Client extends UserClass {

    private int points;
    private String country;
    private String region;
    private String city;
    @OneToMany
    private List<Appointment> appointments;



}
