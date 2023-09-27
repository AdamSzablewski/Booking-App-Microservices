package com.adamszablewski.employmentRequests.util;

import com.adamszablewski.exceptions.NoSuchClientException;
import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.feignClients.UserRepository;
import com.adamszablewski.feignClients.classes.*;
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
    public Owner getOwnerByEmail(String email) {
        UserClass user = getUserByEmail(email);
        return user.getOwner();
    }

    public Client getClientById(long id) {
        UserClass userClass = userRepository.findById(id)
                .orElseThrow(NoSuchUserException::new);
        if (userClass.getClient() == null){
            throw new NoSuchClientException();
        }
        return userClass.getClient();
    }
}
