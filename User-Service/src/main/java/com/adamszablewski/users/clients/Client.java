package com.adamszablewski.users.clients;

import com.adamszablewski.feignClients.Appointment;
import com.adamszablewski.users.UserClass;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Client extends UserClass {

    private int points;
    private String country;
    private String region;
    private String city;
    @OneToMany
    private List<Appointment> appointments;



}
