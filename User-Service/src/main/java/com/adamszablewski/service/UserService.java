package com.adamszablewski.service;

import com.adamszablewski.dto.UserClassDTO;
import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.feignClients.MessagingServiceClient;
import com.adamszablewski.model.UserClass;
import com.adamszablewski.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.adamszablewski.util.Mapper.mapUserToDto;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MessagingServiceClient messagingServiceClient;
    private final Dao dao;

    public UserClassDTO getUserById(long id) {
        UserClassDTO userClassDTO =  mapUserToDto(userRepository.findById(id)
                .orElseThrow(NoSuchUserException::new));
        return UserClassDTO.builder()
                .firstName(userClassDTO.getFirstName())
                .lastName(userClassDTO.getLastName())
                .build();
    }
    @Transactional
    public void deleteUser(String userEmail) {
        UserClass user = userRepository.findByEmail(userEmail)
                .orElseThrow(NoSuchUserException::new);

        dao.deleteUser(user);


    }

    public UserClassDTO getUserByEMail(String userEmail) {
        return mapUserToDto(userRepository.findByEmail(userEmail)
                .orElseThrow(NoSuchUserException::new));
    }

    public String getHashedPassword(String userEmail) {
        UserClass user = userRepository.findByEmail(userEmail)
                .orElseThrow(NoSuchUserException::new);
        return user.getPassword();
    }

    public Boolean validateUser(long userId, String userEmail) {
        Optional<UserClass> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()){
            return false;
        }
        UserClass user = optionalUser.get();
        return user.getEmail().equals(userEmail);
    }
}
