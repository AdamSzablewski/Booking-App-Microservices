package com.adamszablewski.users;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.users.repository.UserRepository;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class User {

    private BigInteger id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    @OneToMany
    private List<Appointment> appointments;




}
