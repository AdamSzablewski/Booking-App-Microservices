package com.adamszablewski.appointments.service;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.helpers.AppointmentHelper;
import com.adamszablewski.appointments.repository.AppointmentRepository;
import com.adamszablewski.exceptions.NoSuchAppointmentException;
import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.users.employee.Employee;


import com.adamszablewski.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    private final AppointmentHelper appointmentHelper;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Optional<Appointment> getAppointmentByPhoneNumber(String number) {
        return appointmentRepository.findByNumber(number);
    }

    public Optional<Appointment> getAppointmentByEmail(String email) {
        return appointmentRepository.findByEmail(email);
    }
    @Transactional
    public ResponseEntity<String> createNewAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
        appointmentHelper.addAppointmentForUsers(appointment);
        return ResponseEntity.ok().build();
    }
    @Transactional
    public ResponseEntity<String> deleteAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);

        appointmentHelper.removeAppointmentFromUsers(appointment);
        appointmentRepository.delete(appointment);
        return ResponseEntity.ok().build();
    }
    @Transactional
    public ResponseEntity<String> deleteAppointmentByEmail(String email) {
        Appointment appointment = appointmentRepository.findByEmail(email)
            .orElseThrow(NoSuchAppointmentException::new);

        appointmentRepository.delete(appointment);
        return ResponseEntity.ok().build();
    }
    @Transactional
    public ResponseEntity<String> deleteAppointmentByNumber(String number) {
        Appointment appointment = appointmentRepository.findByNumber(number)
                .orElseThrow(NoSuchAppointmentException::new);

        appointmentRepository.delete(appointment);
        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<String> changeEmployeeForAppointmentById(Long id, String email) {
        Appointment appointment= appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);

        Employee employee = (Employee) userRepository.findByEmail(email)
                .orElseThrow(NoSuchUserException::new);

        appointmentHelper.changeEmployeeForAppointment(appointment, employee);
        return ResponseEntity.ok().build();
    }
}
