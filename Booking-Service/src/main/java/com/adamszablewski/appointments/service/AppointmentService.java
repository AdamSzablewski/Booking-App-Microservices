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

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Optional<Appointment> getAppointmentByPhoneNumber(String number) {
        return appointmentRepository.findByNumber(number);
    }

    public Optional<Appointment> getAppointmentByEmail(String email) {
        return appointmentRepository.findByEmail(email);
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
        messageSender.createAppoinmentCanceledMessage(appointment);
        appointmentRepository.delete(appointment);
        return ResponseEntity.ok().build();
    }
    @Transactional
    public ResponseEntity<String> deleteAppointmentByEmail(String email) {
        Appointment appointment = appointmentRepository.findByEmail(email)
            .orElseThrow(NoSuchAppointmentException::new);
        messageSender.createAppoinmentCanceledMessage(appointment);
        appointmentRepository.delete(appointment);
        return ResponseEntity.ok().build();
    }
    @Transactional
    public ResponseEntity<String> deleteAppointmentByNumber(String number) {
        Appointment appointment = appointmentRepository.findByNumber(number)
                .orElseThrow(NoSuchAppointmentException::new);
        messageSender.createAppoinmentCanceledMessage(appointment);
        appointmentRepository.delete(appointment);
        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<String> changeEmployeeForAppointmentById(Long id) {
        Appointment appointment= appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);
        appointmentHelper.changeEmployeeForAppointment(appointment, appointment.getEmployee());
        return ResponseEntity.ok().build();
    }
}
