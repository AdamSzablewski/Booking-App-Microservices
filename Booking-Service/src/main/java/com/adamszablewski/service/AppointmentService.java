package com.adamszablewski.service;

import com.adamszablewski.dao.Dao;
import com.adamszablewski.dto.mapper.Mapper;
import com.adamszablewski.exceptions.NotAuthorizedException;
import com.adamszablewski.util.helpers.UserValidator;
import com.adamszablewski.model.Appointment;
import com.adamszablewski.util.helpers.AppointmentHelper;
import com.adamszablewski.repository.AppointmentRepository;
import com.adamszablewski.dto.AppoinmentDTO;
import com.adamszablewski.exceptions.NoSuchAppointmentException;
import com.adamszablewski.messages.MessageSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentHelper appointmentHelper;
    private final MessageSender messageSender;
    private final Dao dao;
    private final UserValidator userValidator;
    private final Mapper mapper;


    public AppoinmentDTO getAppointmentById(Long id, String userEmail) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);

        if (!userValidator.isOwnerOrEmployeeOrTheClient(appointment, userEmail)){
            throw new NotAuthorizedException();
        }
        return mapper.mapAppointmentToDto(appointment);
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
    public void changeEmployeeForAppointmentById(Long id, String employeeEmail, String userEmail) {
        Appointment appointment= appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);
        if (!userValidator.isOwnerOrEmployeeOrTheClient(appointment, userEmail)){
            throw new NotAuthorizedException();
        }
        appointmentHelper.changeEmployeeForAppointment(appointment, appointment.getEmployee(), employeeEmail);
    }

    public void markAppointmentAsDone(Long id, String userEmail) {
        Appointment appointment= appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);

        if (!userValidator.isOwnerOrEmployee(appointment, userEmail)){
            throw new NotAuthorizedException();
        }
        dao.deleteAppoinment(appointment);
        messageSender.sendAppointmentDoneMessage(appointment);
    }
}
