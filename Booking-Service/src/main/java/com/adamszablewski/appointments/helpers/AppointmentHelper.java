package com.adamszablewski.appointments.helpers;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.repository.AppointmentRepository;
import com.adamszablewski.feignClients.classes.Employee;
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
