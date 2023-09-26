package com.adamszablewski.tasks.services;

import com.adamszablewski.dto.TaskDto;
import com.adamszablewski.employmentRequests.util.UserTools;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.adamszablewski.dto.mapper.Mapper.mapTaskToDto;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final FacilityRepository facilityRepository;
    private final UserServiceClient userServiceClient;
    private final MessageSender messageSender;
    private final UserTools userTools;
    public List<TaskDto> getAllTasksForFacilityByName(String name) {
        Facility facility = facilityRepository.findByName(name)
                .orElseThrow(NoSuchFacilityException::new);
        return mapTaskToDto(facility.getTasks());
    }

    public List<TaskDto> getAllTasksforFacilityById(Long id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(NoSuchFacilityException::new);
        return mapTaskToDto(facility.getTasks());
    }

    public TaskDto getServiceById(Long id) {
        return mapTaskToDto(taskRepository.findById(id)
                .orElseThrow(NoSuchTaskException::new));
    }

    public List<TaskDto> getTasksForCity( String city) {
        return mapTaskToDto(taskRepository.findByCity( city));
    }

    public List<TaskDto> getTasksForCityByCategory(String city, String category) {
        return mapTaskToDto(taskRepository.findByCityAndCategory(city, category));
    }
    @Transactional
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
        taskRepository.deleteById(id);
    }
}
