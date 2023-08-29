package com.adamszablewski.users.employee.controllers;

import com.adamszablewski.users.employee.service.EmployeeService;
import com.adamszablewski.users.owners.Owner;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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


}
