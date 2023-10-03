package com.adamszablewski.model;

import com.adamszablewski.util.Identifiable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Client implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private int points;
    @OneToOne
    @ToString.Exclude
    private UserClass user;
    @OneToMany
    @JsonIgnoreProperties("client")
    private Set<Appointment> appointments;

    @Override
    public Long getId() {
        return id;
    }
}

