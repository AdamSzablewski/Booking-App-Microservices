package com.adamszablewski.appointments.repository;

import com.adamszablewski.appointments.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, BigInteger> {
    Optional<Appointment> findByEmail(String email);
    Optional<Appointment> findByNumber(String number);
}
