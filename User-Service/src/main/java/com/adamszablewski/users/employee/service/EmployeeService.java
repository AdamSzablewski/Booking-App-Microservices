package com.adamszablewski.users.employee.service;

import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.users.owners.Owner;
import com.adamszablewski.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeService {
    UserRepository userRepository;
    public Owner getOwnerByEmail(String email) {
        return (Owner) userRepository.findByEmail(email)
                .orElseThrow(NoSuchUserException::new);
    }
}
