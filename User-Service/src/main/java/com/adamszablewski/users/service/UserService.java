package com.adamszablewski.users.service;

import com.adamszablewski.dto.UserClassDTO;
import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.users.UserClass;
import com.adamszablewski.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.adamszablewski.dto.mapper.Mapper.mapUserToDto;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;

    @Transactional
    public void saveAllUsers(List<UserClass> users) {
        userRepository.saveAll(users);
    }

    public UserClassDTO getUserById(long id) {
        return mapUserToDto(userRepository.findById(id)
                .orElseThrow(NoSuchUserException::new));
    }

//    public List<UserClass> getEmployeesByTaskAndFacility(String taskName, String facilityName) {
//        return userRepository.findAllByTask_NameAndFacility_Name(taskName, facilityName);
//    }
}
