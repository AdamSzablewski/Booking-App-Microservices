package com.adamszablewski.users.owners.service;

import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.users.UserClass;
import com.adamszablewski.users.owners.Owner;
import com.adamszablewski.users.owners.repository.OwnerRepository;
import com.adamszablewski.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;


    public Owner getOwnerById(long id) {
        return ownerRepository.findById(id)
                .orElseThrow(NoSuchUserException::new);
    }
    public Owner getOwnerByEmail(String email) {

        UserClass user = userRepository.findByEmail(email)
                .orElseThrow(NoSuchUserException::new);
        return ownerRepository.findByUserId(user.getId())
                .orElseThrow(NoSuchUserException::new);
    }
    @Transactional
    public void makeOwnerFromUser(long id) {
        UserClass user = userRepository.findById(id)
                .orElseThrow(NoSuchUserException::new);

        Owner owner = Owner.builder()
                .user(user)
                .build();
        ownerRepository.save(owner);
    }
}
