package com.adamszablewski.users.employee.controllers;

import com.adamszablewski.users.employee.Employee;
import com.adamszablewski.users.employee.service.EmployeeService;
import com.adamszablewski.users.owners.Owner;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@AllArgsConstructor
@RequestMapping("/users/employee")
public class EmployeeControllerGET {

    EmployeeService employeeService;

    @GetMapping("/email/{email}")
    @ResponseBody
    public Owner getEmployeeByMail(@PathVariable String email){
        return employeeService.getOwnerByEmail(email);
    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public Employee getEmployeeByMail(@PathVariable long id){
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/com/adamszablewski/users/employee")
    List<Employee> getEmployeesByTaskAndFacility(@RequestParam("taskName") String taskName,
                                                 @RequestParam("facilityName") String facilityName){
        return employeeService.getEmployeesByTaskAndFacility(taskName, facilityName);
    }


    @PostMapping("/ids")
    @ResponseBody
    List<Employee> getAllEmployeesByIds(@RequestBody List<Long> ids){
        return employeeService.getAllEmployeesByIds(ids);
    }


}
