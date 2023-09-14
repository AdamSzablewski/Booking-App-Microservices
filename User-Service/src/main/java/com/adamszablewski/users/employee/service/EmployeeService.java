package com.adamszablewski.users.employee.service;

import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.users.UserClass;
import com.adamszablewski.users.employee.Employee;
import com.adamszablewski.users.employee.EmployeeRepository;
import com.adamszablewski.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    UserRepository userRepository;
    EmployeeRepository employeeRepository;
    public Employee getEmployeeByEmail(String email) {
        UserClass user = userRepository.findByEmail(email)
                .orElseThrow(NoSuchUserException::new);
        return employeeRepository.findByUserId(user.getId())
                .orElseThrow(NoSuchUserException::new);
    }
    public List<Employee> getEmployeesByTaskAndFacility(String taskName, String facilityName) {
        return employeeRepository.findAllByTasks_NameAndWorkplace_Name(taskName, facilityName);
    }

    public List<Employee> getAllEmployeesByIds(List<Long> ids) {
        return employeeRepository.findByIdIn(ids);
    }

    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id)
                .orElseThrow(NoSuchUserException::new);
    }

    public void makeEmployeeFromUser(long id, Employee employee) {
        UserClass user = userRepository.findById(id)
                .orElseThrow(NoSuchUserException::new);

        Employee newEmployee = Employee.builder()
                .startTime(employee.getStartTime())
                .endTime(employee.getEndTime())
                .user(user)
                .build();
        employeeRepository.save(newEmployee);
    }
}
