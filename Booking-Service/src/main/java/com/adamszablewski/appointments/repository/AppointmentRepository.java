package com.adamszablewski.appointments.repository;

import com.adamszablewski.appointments.Appointment;

import com.adamszablewski.feignClients.classes.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByEmail(String email);
    Optional<Appointment> findByNumber(String number);
    List<Appointment> findByEmployeeAndDate(long employee, LocalDate date);
}
