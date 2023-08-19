package com.adamszablewski.appointments.helpers;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.repository.AppointmentRepository;
import com.adamszablewski.users.User;
import com.adamszablewski.users.clients.Client;
import com.adamszablewski.users.employee.Employee;
import com.adamszablewski.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AppointmentHelper {
    private final AppointmentRepository appointmentRepository;
    private UserRepository userRepository;

    public void addAppointmentForUsers(User user, Appointment appointment) {
        List<Appointment> appointments = user.getAppointments();
        appointments.add(appointment);
        userRepository.save(user);

    }
    public void addAppointmentForUsers(Appointment appointment) {
        Client client = appointment.getClient();
        List<Appointment> appointments = client.getAppointments();
        appointments.add(appointment);

        Employee employee = appointment.getEmployee();
        List<Appointment> appointmentsEmployee = client.getAppointments();
        appointmentsEmployee.add(appointment);
        userRepository.save(client);
        userRepository.save(employee);

    }

    public void removeAppointmentFromUsers(Appointment appointment) {
        Client client = appointment.getClient();
        client.getAppointments().remove(appointment);
        userRepository.save(client);

        Employee employee = appointment.getEmployee();
        employee.getAppointments().remove(appointment);

        userRepository.save(employee);


    }

    public void changeEmployeeForAppointment(Appointment appointment, Employee employee) {
        Employee oldEmployee =  appointment.getEmployee();
        removeAppointmentFromUser(oldEmployee, appointment);
        appointment.setEmployee(employee);
        addAppointmentForUsers(employee, appointment);
    }
    public void removeAppointmentFromUser(User user, Appointment appointment) {
        user.getAppointments().remove(appointment);
        userRepository.save(user);
    }

}
