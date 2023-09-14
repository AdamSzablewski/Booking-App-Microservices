package com.adamszablewski.users.clients.service;

import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.users.clients.Client;
import com.adamszablewski.users.clients.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientService {

    ClientRepository clientRepository;

//    public Client getClientByMail(String email) {
//        return clientRepository.findByEmail(email)
//                .orElseThrow(NoSuchUserException::new);
//    }

    public Client getClientById(long id) {
        return clientRepository.findById(id)
                .orElseThrow(NoSuchUserException::new);
    }
}
