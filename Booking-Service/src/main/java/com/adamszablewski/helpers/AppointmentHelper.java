package com.adamszablewski.helpers;

import com.adamszablewski.model.Appointment;
import com.adamszablewski.repository.AppointmentRepository;
import com.adamszablewski.model.Employee;
import com.adamszablewski.messages.MessageSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AppointmentHelper {
    private final AppointmentRepository appointmentRepository;
    private final MessageSender messageSender;

    public void changeEmployeeForAppointment(Appointment appointment, Employee employee) {
        appointment.setEmployee(employee);
    }
    public void deleteAppointment(Appointment appointment) {

        appointment.getEmployee().getAppointments().remove(appointment);
        appointment.getClient().getAppointments().remove(appointment);
        appointmentRepository.delete(appointment);

    }

}
