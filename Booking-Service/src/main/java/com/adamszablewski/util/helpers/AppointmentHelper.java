package com.adamszablewski.util.helpers;

import com.adamszablewski.model.Appointment;
import com.adamszablewski.repository.AppointmentRepository;
import com.adamszablewski.model.Employee;
import com.adamszablewski.messages.MessageSender;
import com.adamszablewski.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
@AllArgsConstructor
public class AppointmentHelper {
    private final AppointmentRepository appointmentRepository;
    private final EmployeeRepository employeeRepository;
    private final MessageSender messageSender;
    private final UserTools userTools;
    @Transactional
    public void changeEmployeeForAppointment(Appointment appointment, Employee currentEmployee, String newEmployeeEmail) {
        Employee newEmployee = userTools.getEmployeeByMail(newEmployeeEmail);

        currentEmployee.getAppointments().remove(appointment);
        newEmployee.getAppointments().add(appointment);
        appointment.setEmployee(newEmployee);

        appointmentRepository.save(appointment);
        employeeRepository.saveAll(Arrays.asList(currentEmployee, newEmployee));
        messageSender.sendAppointmentCreatedMessage(appointment, newEmployee.getId());
    }

}
