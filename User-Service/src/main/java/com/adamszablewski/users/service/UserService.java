package com.adamszablewski.users.service;

import com.adamszablewski.users.UserClass;
import com.adamszablewski.users.employee.Employee;
import com.adamszablewski.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;

    @Transactional
    public void saveAllUsers(List<UserClass> users) {
        userRepository.saveAll(users);
    }

//    public List<UserClass> getEmployeesByTaskAndFacility(String taskName, String facilityName) {
//        return userRepository.findAllByTask_NameAndFacility_Name(taskName, facilityName);
//    }
}
