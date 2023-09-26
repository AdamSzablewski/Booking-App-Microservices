package com.adamszablewski.feignClients.classes;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.util.Identifiable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Client implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;
    private int points;
    @OneToOne
    @ToString.Exclude
    private UserClass user;
    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("client")
    private List<Appointment> appointments;

    @Override
    public Long getId() {
        return id;
    }
}

