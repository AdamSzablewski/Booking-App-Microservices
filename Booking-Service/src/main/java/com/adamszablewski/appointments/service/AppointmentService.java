package com.adamszablewski.appointments.service;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.helpers.AppointmentHelper;
import com.adamszablewski.appointments.repository.AppointmentRepository;
import com.adamszablewski.exceptions.NoSuchAppointmentException;
import com.adamszablewski.exceptions.NoSuchUserException;

import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.feignClients.classes.Employee;
import com.adamszablewski.messages.MessageSender;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.listener.MessageAckListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserServiceClient userServiceClient;
    private final MessageSender messageSender;


    private final AppointmentHelper appointmentHelper;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);
    }

    public Appointment getAppointmentByPhoneNumber(String number) {
        return appointmentRepository.findByNumber(number)
                .orElseThrow(NoSuchAppointmentException::new);
    }

    public Appointment getAppointmentByEmail(String email) {
        return appointmentRepository.findByEmail(email)
                .orElseThrow(NoSuchAppointmentException::new);
    }
//    @Transactional
//    public ResponseEntity<String> createNewAppointment(Appointment appointment) {
//        appointmentRepository.save(appointment);
//        appointmentHelper.addAppointmentForUsers(appointment);
//        return ResponseEntity.ok().build();
//    }
    @Transactional
    public ResponseEntity<String> deleteAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);
        return appointmentHelper.deleteAppointment(appointment);
    }
    @Transactional
    public ResponseEntity<String> deleteAppointmentByEmail(String email) {
        Appointment appointment = appointmentRepository.findByEmail(email)
            .orElseThrow(NoSuchAppointmentException::new);
        return appointmentHelper.deleteAppointment(appointment);
    }
    @Transactional
    public ResponseEntity<String> deleteAppointmentByNumber(String number) {
        Appointment appointment = appointmentRepository.findByNumber(number)
                .orElseThrow(NoSuchAppointmentException::new);
        return appointmentHelper.deleteAppointment(appointment);
    }

    @Transactional
    public ResponseEntity<String> changeEmployeeForAppointmentById(Long id) {
        Appointment appointment= appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);
        appointmentHelper.changeEmployeeForAppointment(appointment, appointment.getEmployee());
        return ResponseEntity.ok().build();
    }
}
