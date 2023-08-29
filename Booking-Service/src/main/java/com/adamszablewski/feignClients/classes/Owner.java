package com.adamszablewski.feignClients.classes;

import com.adamszablewski.facilities.Facility;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Owner extends Employee {

    @OneToMany(cascade = CascadeType.ALL)
    private List<Facility> facilities;

}
