package com.adamszablewski.feignClients.classes;

import com.adamszablewski.facilities.Facility;

import com.adamszablewski.util.Identifiable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Builder
public class Owner implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("owner")
    private List<Facility> facilities;
    @OneToOne
    @JsonIgnoreProperties("owner")
    private Employee employee;
    @OneToOne
    private UserClass user;
    @Override
    public Long getId() {
        return id;
    }
}
