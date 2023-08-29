package com.adamszablewski.users.service;


import com.adamszablewski.users.UserInfo;
import com.adamszablewski.users.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserInfoService {

    UserInfoRepository userInfoRepository;

    public List<UserInfo> getAllUsers() {
        return userInfoRepository.findAll();
    }

    public Optional<UserInfo> getUserById(int user_id) {
        return userInfoRepository.findById(user_id);
    }

    public Optional<UserInfo> getUserByName(String username) {
        return userInfoRepository.findByUsername(username);
    }
}
