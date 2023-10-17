package com.adamszablewski.service;

import com.adamszablewski.model.*;
import com.adamszablewski.rabbitMq.RabbitMqProducer;
import com.adamszablewski.repository.AppointmentRepository;
import com.adamszablewski.repository.FacilityRepository;
import com.adamszablewski.repository.TaskRepository;
import com.adamszablewski.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor
@Service
public class Dao {
    private final TaskRepository taskRepository;
    private final AppointmentRepository appointmentRepository;
    private final FacilityRepository facilityRepository;
    private final UserRepository userRepository;
    private final RabbitMqProducer rabbitMqProducer;

    public void deleteTasks(Task task) {
        task.getEmployees().forEach(employee -> {
            employee.getTasks().remove(task);
        });
        task.getAppointments().clear();
        task.getFacility().getTasks().remove(task);

        taskRepository.delete(task);
    }
    public void deleteTasks(Set<Task> tasks) {
        tasks.forEach(this::deleteTask);
    }
    public void deleteTask(Task task) {
        System.out.println("deleting tasks");
        System.out.println(task.getAppointments().size());
        if (task.getAppointments() != null){
            task.getAppointments().forEach(this::deleteAppoinment);
            task.getAppointments().clear();
        }
        task.getEmployees().forEach(employee -> employee.getTasks().remove(task));
        task.getFacility().getTasks().remove(task);

        taskRepository.save(task);

        taskRepository.delete(task);
    }
    public void deleteFacility(Facility facility){
        deleteTasks(facility.getTasks());
        if (facility.getEmployees() != null){
            facility.getEmployees().forEach(employee -> {
                employee.setWorkplace(null);
            });
        }
        facilityRepository.delete(facility);
    }
    public void deleteUser(UserClass user){
        if (user.getOwner() != null && user.getOwner().getFacilities() != null){
            System.out.println("deleting facilities");
            user.getOwner().getFacilities().forEach(this::deleteFacility);
        }
        if(user.getEmployee() != null){
            Employee employee = user.getEmployee();
            if(employee.getWorkplace() != null){
                Facility facility = employee.getWorkplace();
                facility.getEmployees().remove(employee);
                employee.setWorkplace(null);
                facilityRepository.save(facility);
            }
            if(employee.getAppointments() != null){
                Set<Appointment> appointments = employee.getAppointments();
                appointments.forEach(this::deleteAppoinment);
            }
            if (employee.getTasks() != null){
                Set<Task> tasks = employee.getTasks();
                tasks.forEach(task -> {
                    task.getEmployees().remove(employee);
                    employee.getTasks().remove(task);
                });
                taskRepository.saveAll(tasks);
            }
        }
        userRepository.delete(user);
        rabbitMqProducer.sendDeleteForUserMessage(user.getId());
    }

    public void deleteAppoinment(Appointment appointment){
        System.out.println( "delete on appoinment called");
        appointment.getClient().getAppointments().remove(appointment);
        appointment.getEmployee().getAppointments().remove(appointment);
        appointmentRepository.delete(appointment);
        System.out.println("appointment deleted");
    }


}
