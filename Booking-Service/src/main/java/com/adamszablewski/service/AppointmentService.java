package com.adamszablewski.service;

import com.adamszablewski.dao.Dao;
import com.adamszablewski.exceptions.NotAuthorizedException;
import com.adamszablewski.helpers.UserValidator;
import com.adamszablewski.model.Appointment;
import com.adamszablewski.helpers.AppointmentHelper;
import com.adamszablewski.repository.AppointmentRepository;
import com.adamszablewski.dto.AppoinmentDTO;
import com.adamszablewski.exceptions.NoSuchAppointmentException;
import com.adamszablewski.messages.MessageSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import static com.adamszablewski.dto.mapper.Mapper.mapAppointmentToDto;


@Service
@AllArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentHelper appointmentHelper;
    private final MessageSender messageSender;
    private final Dao dao;
    private final UserValidator userValidator;


    public AppoinmentDTO getAppointmentById(Long id, String userEmail) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);

        if (!userValidator.isOwnerEmployeeOrTheClient(appointment, userEmail)){
            throw new NotAuthorizedException();
        }
        return mapAppointmentToDto(appointment);
    }
    public void deleteAppointmentById(Long id, String userEmail) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);
        if (!userValidator.isOwner(appointment.getFacility(), userEmail)){
            throw new NotAuthorizedException();
        }
        dao.deleteAppoinment(appointment);
        messageSender.createAppoinmentCanceledMessage(appointment);
    }
    public void changeEmployeeForAppointmentById(Long id, String userEmail) {
        Appointment appointment= appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);
        if (!userValidator.isOwnerEmployeeOrTheClient(appointment, userEmail)){
            throw new NotAuthorizedException();
        }
        appointmentHelper.changeEmployeeForAppointment(appointment, appointment.getEmployee());
    }

    public void markAppointmentAsDone(Long id, String userEmail) {
        Appointment appointment= appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);

        if (!userValidator.isEmployee(appointment.getFacility(), userEmail)
                && !userValidator.isOwner(appointment.getFacility(), userEmail)){
            throw new NotAuthorizedException();
        }
        appointmentHelper.deleteAppointment(appointment);
        messageSender.sendAppointmentDoneMessage(appointment);
    }
}
