package com.adamszablewski.users.clients.service;

import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.users.clients.Client;
import com.adamszablewski.users.owners.Owner;
import com.adamszablewski.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientService {

    UserRepository userRepository;

    public Client getClientByMail(String email) {
        return (Client) userRepository.findByEmail(email)
                .orElseThrow(NoSuchUserException::new);
    }

    public Client getClientById(long id) {
        return (Client) userRepository.findById(id)
                .orElseThrow(NoSuchUserException::new);
    }
}
