package com.adamszablewski.service;

import com.adamszablewski.dto.OwnerDto;
import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.exceptions.OwnerAlreadyCreatedException;
import com.adamszablewski.model.UserClass;
import com.adamszablewski.model.Owner;
import com.adamszablewski.repository.OwnerRepository;
import com.adamszablewski.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.adamszablewski.util.Mapper.mapOwnerToDto;

@Service
@AllArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;


    public OwnerDto getOwnerById(long id) {
        return mapOwnerToDto(ownerRepository.findById(id)
                .orElseThrow(NoSuchUserException::new));
    }
    public OwnerDto getOwnerByEmail(String email) {

        UserClass user = userRepository.findByEmail(email)
                .orElseThrow(NoSuchUserException::new);
        return mapOwnerToDto(user.getOwner());
    }
    @Transactional
    public void makeOwnerFromUser(long id) {
        UserClass user = userRepository.findById(id)
                .orElseThrow(NoSuchUserException::new);

        Owner owner = Owner.builder()
                .user(user)
                .build();
        if (user.getOwner() != null){
            throw new OwnerAlreadyCreatedException();
        }
        user.setOwner(owner);
        ownerRepository.save(owner);
    }
}
