package com.adamszablewski.service;

import com.adamszablewski.model.Appointment;
import com.adamszablewski.model.Task;
import com.adamszablewski.repository.AppointmentRepository;
import com.adamszablewski.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor
@Service
public class Dao {
    private final TaskRepository taskRepository;
    private final AppointmentRepository appointmentRepository;

    public void deleteTasks(Task task) {
//        task.getAppointments().forEach(
//                appointment -> {
//                    appointment.setFacility(null);
//                    appointment.getClient().getAppointments().remove(appointment);
//                    appointment.getEmployee().setTasks(null);
//                    appointment.getEmployee().setAppointments(null);
//                });
//        appointmentRepository.deleteAll(task.getAppointments());
//        taskRepository.delete(task);
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

        task.getAppointments().forEach(this::deleteAppoinment);
        task.getAppointments().clear();

        task.getEmployees().forEach(employee -> employee.getTasks().remove(task));
        task.getFacility().getTasks().remove(task);

        taskRepository.save(task);


        taskRepository.delete(task);
    }

    public void deleteAppoinment(Appointment appointment){
        appointment.getClient().getAppointments().remove(appointment);
        appointment.getEmployee().getAppointments().remove(appointment);
        appointmentRepository.delete(appointment);
    }


}
