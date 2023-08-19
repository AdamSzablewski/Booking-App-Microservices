package com.adamszablewski.users.employee;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.services.Service;
import com.adamszablewski.users.User;
import com.adamszablewski.users.repository.UserRepository;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends User {
    private Facility workplace;
    private List<Service> services;




}
