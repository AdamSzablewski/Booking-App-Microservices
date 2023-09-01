package com.adamszablewski.tasks.controllers;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.tasks.Task;
import com.adamszablewski.tasks.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/services")
@AllArgsConstructor
public class TaskControllerGET {

    TaskService serviceService;

    @GetMapping("/facility/name/{name}")
    public List<Task> getAllServicesFOrFacilityByName(@PathVariable String name){
        return serviceService.getAllTasksForFacilityByName(name);
    }
    @GetMapping("/facility/id/{id}")
    public List<Task> getAllTasksForFacilityById(@PathVariable Long id){
        return serviceService.getAllTasksforFacilityById(id);
    }
    @GetMapping("/id/{id}")
    public Task getServiceById(@PathVariable Long id){
        return serviceService.getServiceById(id);
    }
    @GetMapping("/city/{city}")
    @ResponseBody
    public List<Task> getTasksForCity( @PathVariable String city){
        return serviceService.getTasksForCity(city);
    }
    @GetMapping("/city/{city}/category/{category}")
    public List<Task> getTasksForCityByCategory(@PathVariable String city,
                                                @PathVariable String category){
        return serviceService.getTasksForCityByCategory(city, category);
    }



}
