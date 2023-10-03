package com.adamszablewski.service;

import com.adamszablewski.dto.TaskDto;
import com.adamszablewski.helpers.UserTools;
import com.adamszablewski.exceptions.NoSuchEmployeeException;
import com.adamszablewski.exceptions.NoSuchFacilityException;
import com.adamszablewski.exceptions.NoSuchTaskException;
import com.adamszablewski.model.Facility;
import com.adamszablewski.repository.FacilityRepository;
import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.model.Employee;
import com.adamszablewski.messages.MessageSender;
import com.adamszablewski.model.Task;
import com.adamszablewski.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.adamszablewski.dto.mapper.Mapper.mapTaskToDto;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final FacilityRepository facilityRepository;
    private final UserServiceClient userServiceClient;
    private final MessageSender messageSender;
    private final UserTools userTools;
    public Set<TaskDto> getAllTasksForFacilityByName(String name) {
        Facility facility = facilityRepository.findByName(name)
                .orElseThrow(NoSuchFacilityException::new);
        return mapTaskToDto(facility.getTasks());
    }

    public Set<TaskDto> getAllTasksforFacilityById(Long id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(NoSuchFacilityException::new);
        return mapTaskToDto(facility.getTasks());
    }

    public TaskDto getServiceById(Long id) {
        return mapTaskToDto(taskRepository.findById(id)
                .orElseThrow(NoSuchTaskException::new));
    }

    public Set<TaskDto> getTasksForCity( String city) {
        return mapTaskToDto(taskRepository.findByCity( city));
    }

    public Set<TaskDto> getTasksForCityByCategory(String city, String category) {
        return mapTaskToDto(taskRepository.findByCityAndCategory(city, category));
    }

    public void createTaskForFacility(long id, Task task) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(NoSuchFacilityException::new);
        Task newTask = Task.builder()
                .region(facility.getRegion())
                .city(facility.getCity())
                .category(task.getCategory())
                .name(task.getName())
                .price(task.getPrice())
                .durationInMinutes(task.getDurationInMinutes())
                .currency(task.getCurrency())
                .facility(facility)
                .build();
        taskRepository.save(newTask);

        facility.getTasks().add(newTask);
        facilityRepository.save(facility);
        messageSender.sendTaskCreatedMessage(facility, newTask);

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

        Employee employee = userTools.getEmployeeByUserId(id);
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
        Task task = taskRepository.findById(id)
                        .orElseThrow(NoSuchTaskException::new);
        task.getEmployees().forEach(employee -> {
            employee.getTasks().remove(task);
        });
        task.getAppointments().clear();
        task.getFacility().getTasks().remove(task);

        taskRepository.delete(task);
    }
}
