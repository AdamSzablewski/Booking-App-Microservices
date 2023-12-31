package com.adamszablewski.dao;

import com.adamszablewski.model.Appointment;
import com.adamszablewski.model.Facility;
import com.adamszablewski.model.Task;
import com.adamszablewski.model.UserClass;
import com.adamszablewski.repository.AppointmentRepository;
import com.adamszablewski.repository.FacilityRepository;
import com.adamszablewski.repository.TaskRepository;
import com.adamszablewski.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@AllArgsConstructor
@Service
public class Dao {
    private final TaskRepository taskRepository;
    private final AppointmentRepository appointmentRepository;
    private final FacilityRepository facilityRepository;
    private final UserRepository userRepository;

    public void deleteTasks(Task task) {
        task.getEmployees().forEach(employee -> {
            employee.getTasks().remove(task);
        });
        task.getAppointments().clear();
        task.getFacility().getTasks().remove(task);

        taskRepository.delete(task);
    }
    @Transactional
    public void deleteTasks(Set<Task> tasks) {
        tasks.forEach(this::deleteTask);
    }
    @Transactional
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
    public void deleteUser(UserClass userClass){
        if (userClass.getOwner() != null && userClass.getOwner().getFacilities() != null){
            userClass.getOwner().getFacilities().forEach(this::deleteFacility);
        }
        userRepository.delete(userClass);
    }
    @Transactional
    public void deleteAppoinment(Appointment appointment){

        appointment.getClient().getAppointments().remove(appointment);
        appointment.getEmployee().getAppointments().remove(appointment);
        appointmentRepository.delete(appointment);

    }


}
