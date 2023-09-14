package com.adamszablewski.feignClients.classes;

import com.adamszablewski.facilities.Facility;

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
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Facility> facilities;
    @OneToOne
    private Employee employee;
    @OneToOne
    private UserClass user;

}
