package com.adamszablewski.users.employee.service;

import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.users.employee.Employee;
import com.adamszablewski.users.employee.EmployeeRepository;
import com.adamszablewski.users.owners.Owner;
import com.adamszablewski.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    UserRepository userRepository;
    EmployeeRepository employeeRepository;
//    public Employee getEmployeeByEmail(String email) {
//        return employeeRepository.findByEmail(email)
//                .orElseThrow(NoSuchUserException::new);
//    }
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
}
