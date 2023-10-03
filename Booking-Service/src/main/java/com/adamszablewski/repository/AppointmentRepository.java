package com.adamszablewski.repository;

import com.adamszablewski.model.Appointment;

import com.adamszablewski.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByEmail(String email);

    Optional<Appointment> findByNumber(String number);
    List<Appointment> findByEmployeeAndDate(Employee employee, LocalDate date);
}
