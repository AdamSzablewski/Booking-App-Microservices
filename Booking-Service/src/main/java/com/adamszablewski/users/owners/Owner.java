package com.adamszablewski.users.owners;

import com.adamszablewski.facilities.Facility;
import com.adamszablewski.users.User;
import com.adamszablewski.users.employee.Employee;
import jakarta.persistence.CascadeType;
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
public class Owner extends Employee {

    @OneToMany(cascade = CascadeType.ALL)
    private List<Facility> facilities;

}
