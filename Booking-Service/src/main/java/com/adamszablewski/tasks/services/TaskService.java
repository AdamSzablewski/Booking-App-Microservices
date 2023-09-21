package com.adamszablewski.tasks.services;

import com.adamszablewski.appointments.dtos.RestResponseDTO;
import com.adamszablewski.exceptions.ConnectionException;
import com.adamszablewski.exceptions.NoSuchEmployeeException;
import com.adamszablewski.exceptions.NoSuchFacilityException;
import com.adamszablewski.exceptions.NoSuchTaskException;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.facilities.repository.FacilityRepository;
import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.feignClients.classes.Employee;
import com.adamszablewski.messages.MessageSender;
import com.adamszablewski.tasks.Task;
import com.adamszablewski.tasks.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.ConnectException;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final FacilityRepository facilityRepository;
    private final UserServiceClient userServiceClient;
    private final MessageSender messageSender;
    public List<Task> getAllTasksForFacilityByName(String name) {
        Facility facility = facilityRepository.findByName(name)
                .orElseThrow(NoSuchFacilityException::new);
        return facility.getTasks();
    }

    public List<Task> getAllTasksforFacilityById(Long id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(NoSuchFacilityException::new);
        return facility.getTasks();
    }

    public Task getServiceById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(NoSuchTaskException::new);
    }

    public List<Task> getTasksForCity( String city) {
        return taskRepository.findByCity( city);
    }

    public List<Task> getTasksForCityByCategory(String city, String category) {
        return taskRepository.findByCityAndCategory(city, category);
    }
    @Transactional
    public void createTaskForFacility(long id, Task task) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(NoSuchFacilityException::new);
        task.setFacility(facility);
        facility.getTasks().add(task);
        facilityRepository.save(facility);
        messageSender.sendTaskCreatedMessage(facility, task);

    }
    @Transactional
    public void changeTask(long id, Task newTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(NoSuchTaskException::new);

        task.setCurrency(newTask.getCurrency());
        task.setCategory(newTask.getCategory());
        task.setName(newTask.getName());
        task.setPrice(newTask.getPrice());

        taskRepository.save(task);

    }
    @Transactional
    public void removeEmployeeFromTask(long id, long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(NoSuchTaskException::new);
        Employee employee = userServiceClient.findEmployeeById(id).getValue();
        employee.getTasks().remove(task);
        task.getEmployees().remove(employee);
        taskRepository.save(task);
    }
    @Transactional
    public void addEmployeeTOTask(long id, long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(NoSuchTaskException::new);

        Employee employee = userServiceClient.findEmployeeById(id).getValue();
        System.out.println(employee);
        System.out.println("employees for facility "+employee.getWorkplace());
        System.out.println(task);
        if (employee.getWorkplace() == null || employee.getWorkplace().getId() != task.getFacility().getId()){
            throw new NoSuchEmployeeException();
        }
        task.getEmployees().add(employee);
        employee.getTasks().add(task);

        taskRepository.save(task);
    }

    public void deleteTaskById(long id) {
        taskRepository.deleteById(id);
    }
}
