package com.adamszablewski.users.clients;

import com.adamszablewski.Identifiable;
import com.adamszablewski.feignClients.Appointment;
import com.adamszablewski.users.UserClass;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private UserClass user;
    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("client")
    private List<Appointment> appointments;

    @Override
    public Long getId() {
        return id;
    }
}