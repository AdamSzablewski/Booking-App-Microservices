package com.adamszablewski.service;

import com.adamszablewski.dto.mapper.Mapper;
import com.adamszablewski.model.Appointment;
import com.adamszablewski.helpers.AppointmentHelper;
import com.adamszablewski.repository.AppointmentRepository;
import com.adamszablewski.dto.AppoinmentDTO;
import com.adamszablewski.exceptions.NoSuchAppointmentException;
import com.adamszablewski.messages.MessageSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.adamszablewski.dto.mapper.Mapper.mapAppointmentToDto;


@Service
@AllArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentHelper appointmentHelper;
    private final MessageSender messageSender;
    private final Dao dao;

    public Set<AppoinmentDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments.stream()
                .map(Mapper::mapAppointmentToDto)
                .collect(Collectors.toSet());
    }

    public AppoinmentDTO getAppointmentById(Long id) {
        return mapAppointmentToDto(appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new));
    }
    public void deleteAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);
        dao.deleteAppoinment(appointment);
        messageSender.createAppoinmentCanceledMessage(appointment);
    }
    public void changeEmployeeForAppointmentById(Long id) {
        Appointment appointment= appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);
        appointmentHelper.changeEmployeeForAppointment(appointment, appointment.getEmployee());
    }

    public void markAppointmentAsDone(Long id) {
        Appointment appointment= appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);
        appointmentHelper.deleteAppointment(appointment);
        messageSender.sendAppointmentDoneMessage(appointment);
    }
}
