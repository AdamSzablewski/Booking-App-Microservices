package com.adamszablewski.services;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.facilities.Facility;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Service {
    @Id
    private BigInteger id;
    private String name;
    @OneToMany
    private List<Appointment> appointments;
    @ManyToOne
    private Facility facility;
    private double price;
    private String currency;
}
