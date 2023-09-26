package com.adamszablewski.appointments.service;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.helpers.AppointmentHelper;
import com.adamszablewski.appointments.repository.AppointmentRepository;
import com.adamszablewski.dto.AppoinmentDTO;
import com.adamszablewski.exceptions.NoSuchAppointmentException;
import com.adamszablewski.messages.MessageSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

import static com.adamszablewski.dto.mapper.Mapper.mapAppointmentToDto;


@Service
@AllArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentHelper appointmentHelper;
    private final MessageSender messageSender;

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
