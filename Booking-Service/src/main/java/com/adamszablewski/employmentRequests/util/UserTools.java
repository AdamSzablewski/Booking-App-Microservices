package com.adamszablewski.employmentRequests.util;

import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.feignClients.UserRepository;
import com.adamszablewski.feignClients.classes.Employee;
import com.adamszablewski.feignClients.classes.EmployeeRepository;
import com.adamszablewski.feignClients.classes.Owner;
import com.adamszablewski.feignClients.classes.UserClass;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserTools {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final OwnerRepository ownerRepository;

    public Employee getEmployeeByMail(String mail){
        UserClass user = userRepository.findByEmail(mail)
                .orElseThrow(NoSuchUserException::new);
        return employeeRepository.findByUserId(user.getId())
                .orElseThrow(NoSuchUserException::new);
    }
    public Employee getEmployeeByUserId(long id){
        UserClass user = userRepository.findById(id)
                .orElseThrow(NoSuchUserException::new);
        return employeeRepository.findByUserId(user.getId())
                .orElseThrow(NoSuchUserException::new);
    }

    public UserClass getUserByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(NoSuchUserException::new);
    }

    public Owner getOwnerByUserId(long id) {
        return ownerRepository.findByUserId(id)
                .orElseThrow(NoSuchUserException::new);
    }
}
