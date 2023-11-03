package com.adamszablewski.service;

import com.adamszablewski.dao.Dao;
import com.adamszablewski.dto.TaskDto;
import com.adamszablewski.exceptions.*;
import com.adamszablewski.model.Category;
import com.adamszablewski.repository.CategoryRepository;
import com.adamszablewski.util.CategoryUtil;
import com.adamszablewski.util.UserTools;
import com.adamszablewski.util.UserValidator;
import com.adamszablewski.model.Facility;
import com.adamszablewski.repository.FacilityRepository;
import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.model.Employee;
import com.adamszablewski.messages.MessageSender;
import com.adamszablewski.model.Task;
import com.adamszablewski.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.support.TaskUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final UserValidator userValidator;
    private final CategoryUtil categoryUtil;
    private final CategoryRepository categoryRepository;
    private final Dao dao;
    public Set<TaskDto> getAllTasksForFacilityByFacilityName(String name) {
        Facility facility = facilityRepository.findByName(name)
                .orElseThrow(NoSuchFacilityException::new);
        return mapTaskToDto(facility.getTasks(), true);
    }

    public Set<TaskDto> getAllTasksforFacilityByFacilityId(Long id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(NoSuchFacilityException::new);
        return mapTaskToDto(facility.getTasks());
    }

    public TaskDto getServiceById(Long id) {
        return mapTaskToDto(taskRepository.findById(id)
                .orElseThrow(NoSuchTaskException::new));
    }


    public Set<TaskDto> getTasksForCityTop30( String city) {
        Pageable pageable = PageRequest.of(0,30);
        Set<Task> tasks = taskRepository.findByCityTop(city, pageable).toSet();
        return mapTaskToDto(tasks, true);
    }
    public Set<TaskDto> getTasksForCityByText(String city, String text) {
        Pageable pageable = PageRequest.of(0,50);
        Set<Task> tasks = taskRepository.findByCityAndNameTop(city, text, pageable).toSet();
        return mapTaskToDto(tasks, true);
    }
    public Set<TaskDto> getTasksForCityByCategory( String city, String category) {
        Pageable pageable = PageRequest.of(0,30);
        Set<Task> tasks = taskRepository.findByCityTopAndCategoryTop(city, category, pageable).toSet();
        return mapTaskToDto(tasks, true);
    }

    public void createTaskForFacility(long id, Task task, String userEmail) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(NoSuchFacilityException::new);

        if (!userValidator.isOwner(facility, userEmail)){
            throw new NotAuthorizedException();
        }
        if (!categoryUtil.checkIfExists(task.getCategory().getName())){
            throw new NoSuchCategoryException();
        }

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
    public void changeTask(long id, Task newTask, String userEmail) {
        Task task = taskRepository.findById(id)
                .orElseThrow(NoSuchTaskException::new);
        if (userValidator.isOwner(task.getFacility(), userEmail)){
            throw new NotAuthorizedException();
        }
        task.setCurrency(newTask.getCurrency());
        changeCategory(task);
        task.setName(newTask.getName());
        task.setPrice(newTask.getPrice());

        taskRepository.save(task);

    }
    private void changeCategory(Task task){
        Category category = categoryRepository.findByName(task.getCategory().getName())
                .orElseThrow(NoSuchCategoryException::new);
        task.setCategory(category);
    }
    @Transactional
    public void removeEmployeeFromTask(long id, long taskId, String userEmail) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(NoSuchTaskException::new);
        if (!userValidator.isOwner(task.getFacility(), userEmail)){
            throw new NotAuthorizedException();
        }
        Employee employee = userServiceClient.findEmployeeById(id).getValue();
        employee.getTasks().remove(task);
        task.getEmployees().remove(employee);

        taskRepository.save(task);
    }
    @Transactional
    public void addEmployeeTOTask(long id, long taskId, String userEmail) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(NoSuchTaskException::new);

        if (!userValidator.isOwner(task.getFacility(), userEmail)){
            throw new NotAuthorizedException();
        }
        Employee employee = userTools.getEmployeeByUserId(id);
        if (employee.getWorkplace() == null || employee.getWorkplace().getId() != task.getFacility().getId()){
            throw new NoSuchEmployeeException();
        }
        task.getEmployees().add(employee);
        employee.getTasks().add(task);

        taskRepository.save(task);
    }

    public void deleteTaskById(long id, String userEmail) {
        Task task = taskRepository.findById(id)
                        .orElseThrow(NoSuchTaskException::new);
        if (!userValidator.isOwner(task.getFacility(), userEmail)){
            throw new NotAuthorizedException();
        }
        dao.deleteTask(task);
    }
}
