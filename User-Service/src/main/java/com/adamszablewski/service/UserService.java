package com.adamszablewski.service;

import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.dto.UserClassDTO;
import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.feignClients.BookingFeignClient;
import com.adamszablewski.feignClients.MessagingServiceClient;
import com.adamszablewski.model.Task;
import com.adamszablewski.model.UserClass;
import com.adamszablewski.repository.FacilityRepository;
import com.adamszablewski.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static com.adamszablewski.dto.mapper.Mapper.mapUserToDto;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MessagingServiceClient messagingServiceClient;
    private final BookingFeignClient bookingFeignClient;
    private final FacilityRepository facilityRepository;
    private final Dao dao;

    @Transactional
    public void saveAllUsers(Set<UserClass> users) {
        userRepository.saveAll(users);
    }

    public UserClassDTO getUserById(long id) {
        return mapUserToDto(userRepository.findById(id)
                .orElseThrow(NoSuchUserException::new));
    }
@Transactional
    public void deleteUser(long id) {
        UserClass user = userRepository.findById(id)
                .orElseThrow(NoSuchUserException::new);

        dao.deleteUser(user);
        RestResponseDTO<String> responseDTO = messagingServiceClient.deleteConversation(id);
        userRepository.deleteById(id);

        if (responseDTO.getError() != null ){
            throw new RuntimeException("Conversation not deleted");
        }

    }

//    public List<UserClass> getEmployeesByTaskAndFacility(String taskName, String facilityName) {
//        return userRepository.findAllByTask_NameAndFacility_Name(taskName, facilityName);
//    }
}
