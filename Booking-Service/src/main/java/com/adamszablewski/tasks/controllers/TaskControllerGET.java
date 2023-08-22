package com.adamszablewski.tasks.controllers;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.tasks.Task;
import com.adamszablewski.tasks.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/services")
@AllArgsConstructor
public class TaskControllerGET {

    TaskService serviceService;

    @GetMapping("/facility/name/{name}")
    public List<Task> getAllServicesFOrFacilityByName(@RequestParam String name){
        return serviceService.getAllTasksForFacilityByName(name);
    }
    @GetMapping("/facility/id/{id}")
    public List<Task> getAllTasksForFacilityById(@RequestParam Long id){
        return serviceService.getAllTasksforFacilityById(id);
    }
    @GetMapping("/id/{id}")
    public Task getServiceById(@RequestParam Long id){
        return serviceService.getServiceById(id);
    }
    @GetMapping("/region/{region}/city{city}")
    public List<Task> getTasksForCity(@RequestParam String region, @RequestParam String city){
        return serviceService.getTasksForCity(region, city);
    }
    @GetMapping("/region/{region}/city{city}/category/{category}")
    public List<Task> getTasksForCityByCategory(@RequestParam String region,
                                                   @RequestParam String city,
                                                   @RequestParam String category){
        return serviceService.getTasksForCityByCategory(region, city, category);
    }



}
