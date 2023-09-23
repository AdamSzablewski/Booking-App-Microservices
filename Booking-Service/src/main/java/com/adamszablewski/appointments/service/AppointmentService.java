package com.adamszablewski.appointments.service;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.helpers.AppointmentHelper;
import com.adamszablewski.appointments.repository.AppointmentRepository;
import com.adamszablewski.dto.AppoinmentDTO;
import com.adamszablewski.dto.mapper.Mapper;
import com.adamszablewski.exceptions.NoSuchAppointmentException;

import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.messages.MessageSender;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.adamszablewski.dto.mapper.Mapper.mapAppointmentToDto;
import static com.adamszablewski.dto.mapper.Mapper.mapAppointmentToDto;

@Service
@AllArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentHelper appointmentHelper;

    public List<AppoinmentDTO> getAllAppointments() {
        return mapAppointmentToDto(appointmentRepository.findAll());
    }

    public AppoinmentDTO getAppointmentById(Long id) {
        return mapAppointmentToDto(appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new));
    }
    public void deleteAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);
        appointmentHelper.deleteAppointment(appointment);
    }
    public void changeEmployeeForAppointmentById(Long id) {
        Appointment appointment= appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);
        appointmentHelper.changeEmployeeForAppointment(appointment, appointment.getEmployee());
    }
}
