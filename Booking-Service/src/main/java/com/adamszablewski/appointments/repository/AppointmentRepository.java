package com.adamszablewski.appointments.repository;

import com.adamszablewski.appointments.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByEmail(String email);
    Optional<Appointment> findByNumber(String number);
}
