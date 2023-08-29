package com.adamszablewski.appointments.helpers;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.repository.AppointmentRepository;

import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.feignClients.classes.Client;
import com.adamszablewski.feignClients.classes.Employee;
import com.adamszablewski.feignClients.classes.UserClass;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
public class AppointmentHelper {
    private final AppointmentRepository appointmentRepository;
    private final UserServiceClient userServiceClient;

    public void addAppointmentForUsers(UserClass user, Appointment appointment) {
        List<Appointment> appointments = user.getAppointments();
        appointments.add(appointment);
        userServiceClient.save(user);

    }
    @Transactional
    public void addAppointmentForUsers(Appointment appointment) {
        Client client = appointment.getClient();
        List<Appointment> appointments = client.getAppointments();
        appointments.add(appointment);

        Employee employee = appointment.getEmployee();
        List<Appointment> appointmentsEmployee = client.getAppointments();
        appointmentsEmployee.add(appointment);
        userServiceClient.saveAllUsers(List.of(employee, client));
    }

    public void removeAppointmentFromUsers(Appointment appointment) {
        Client client = appointment.getClient();
        client.getAppointments().remove(appointment);


        Employee employee = appointment.getEmployee();
        employee.getAppointments().remove(appointment);

        userServiceClient.saveAllUsers(List.of(client, employee));


    }

    public void changeEmployeeForAppointment(Appointment appointment, Employee employee) {
        Employee oldEmployee =  appointment.getEmployee();
        removeAppointmentFromUser(oldEmployee, appointment);
        appointment.setEmployee(employee);
        addAppointmentForUsers(employee, appointment);
    }
    public void removeAppointmentFromUser(UserClass user, Appointment appointment) {
        user.getAppointments().remove(appointment);
        userServiceClient.save(user);
    }

}
