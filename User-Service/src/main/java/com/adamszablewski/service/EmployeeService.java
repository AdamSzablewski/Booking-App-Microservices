package com.adamszablewski.service;

import com.adamszablewski.exceptions.EmployeeAlreadyCreatedException;
import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.model.UserClass;
import com.adamszablewski.model.Employee;
import com.adamszablewski.repository.EmployeeRepository;
import com.adamszablewski.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class EmployeeService {
    UserRepository userRepository;
    EmployeeRepository employeeRepository;
    public Employee getEmployeeByEmail(String email) {
        UserClass user = userRepository.findByEmail(email)
                .orElseThrow(NoSuchUserException::new);
        System.out.println(user);
        return employeeRepository.findByUserId(user.getId())
                .orElseThrow(NoSuchUserException::new);
    }
    public Set<Employee> getEmployeesByTaskAndFacility(String taskName, String facilityName) {
        return employeeRepository.findAllByTasks_NameAndWorkplace_Name(taskName, facilityName);
    }

    public Set<Employee> getAllEmployeesByIds(List<Long> ids) {
        return employeeRepository.findByIdIn(ids);
    }

    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id)
                .orElseThrow(NoSuchUserException::new);
    }

    public void makeEmployeeFromUser(long id) {
        UserClass user = userRepository.findById(id)
                .orElseThrow(NoSuchUserException::new);
        if (user.getEmployee() != null){
            throw new EmployeeAlreadyCreatedException();
        }

        Employee newEmployee = Employee.builder()
                .user(user)
                .build();
        user.setEmployee(newEmployee);
        employeeRepository.save(newEmployee);
    }

    public void setWorkingHours(long id, LocalTime startTIme, LocalTime endTIme) {
        UserClass user = userRepository.findById(id)
                .orElseThrow(NoSuchUserException::new);
        Employee employee = user.getEmployee();
        employee.setStartTime(startTIme);
        employee.setEndTime(endTIme);
        employeeRepository.save(employee);
    }
}
