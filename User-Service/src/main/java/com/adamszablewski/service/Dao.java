package com.adamszablewski.service;

import com.adamszablewski.model.*;
import com.adamszablewski.rabbitMq.RabbitMqProducer;
import com.adamszablewski.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class Dao {
    private final TaskRepository taskRepository;
    private final AppointmentRepository appointmentRepository;
    private final FacilityRepository facilityRepository;
    private final UserRepository userRepository;
    private final RabbitMqProducer rabbitMqProducer;
    private final ClientRepository clientRepository;
    @Transactional
    public void deleteTasks(Task task) {
        task.getEmployees().forEach(employee -> {
            employee.getTasks().remove(task);
        });
        task.getAppointments().clear();
        task.getFacility().getTasks().remove(task);

        taskRepository.delete(task);
    }

    public void deleteTasks(Set<Task> tasks) {
        Set<Task> tasksCopy = new HashSet<>(tasks); // Create a copy of the set

        for (Task task : tasksCopy) {
            deleteTask(task);
        }
    }

    public void deleteTask(Task task) {
        if (task.getAppointments() != null){
            task.getAppointments().forEach(this::deleteAppoinment);
            task.getAppointments().clear();
        }
        task.getEmployees().forEach(employee -> employee.getTasks().remove(task));
        task.getFacility().getTasks().remove(task);

        taskRepository.save(task);

        taskRepository.delete(task);
    }
    @Transactional
    public void deleteFacility(Facility facility){
        deleteTasks(facility.getTasks());
        if (facility.getEmployees() != null){
            facility.getEmployees().forEach(employee -> {
                employee.setWorkplace(null);
            });
        }
        facilityRepository.delete(facility);
    }
    @Transactional
    public void deleteUser(UserClass user){
        if (user.getOwner() != null && user.getOwner().getFacilities() != null){
            user.getOwner().getFacilities().forEach(this::deleteFacility);
        }
        if(user.getEmployee() != null){
            deleteEmployee(user.getEmployee());
        }
        if (user.getClient() != null){
            deleteClient(user.getClient());
        }
        userRepository.delete(user);
        rabbitMqProducer.sendDeleteForUserMessage(user.getId());
    }
    @Transactional
    private void deleteClient(Client client) {
        client.getAppointments().forEach(this::deleteAppoinment);
        clientRepository.delete(client);
    }
    @Transactional
    private void removeEmployeeFromTasks(Employee employee, Set<Task> tasks){
        tasks.forEach(task -> {
            task.getEmployees().remove(employee);
            employee.getTasks().remove(task);
        });
        taskRepository.saveAll(tasks);

    }

    @Transactional
    public void deleteEmployee(Employee employee) {
        if (employee.getWorkplace() != null) {
            Facility facility = employee.getWorkplace();
            facility.getEmployees().remove(employee);
            employee.setWorkplace(null);
            facilityRepository.save(facility);
        }
        if (employee.getAppointments() != null) {
            Set<Appointment> appointments = employee.getAppointments();
            appointments.forEach(this::deleteAppoinment);
        }
        if(employee.getTasks() != null){
            removeEmployeeFromTasks(employee, employee.getTasks());
        }

    }
    @Transactional
    public void deleteAppoinment(Appointment appointment){
        appointment.getClient().getAppointments().remove(appointment);
        appointment.getEmployee().getAppointments().remove(appointment);
        appointmentRepository.delete(appointment);

    }


}
