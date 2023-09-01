package com.adamszablewski.tasks.services;

import com.adamszablewski.exceptions.NoSuchFacilityException;
import com.adamszablewski.exceptions.NoSuchTaskException;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.facilities.repository.FacilityRepository;
import com.adamszablewski.tasks.Task;
import com.adamszablewski.tasks.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    TaskRepository taskRepository;
    FacilityRepository facilityRepository;
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
}
