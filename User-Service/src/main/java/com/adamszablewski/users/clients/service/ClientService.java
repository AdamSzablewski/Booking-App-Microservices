package com.adamszablewski.users.clients.service;

import com.adamszablewski.dto.ClientDto;
import com.adamszablewski.exceptions.ClientAlreadyCreatedException;
import com.adamszablewski.exceptions.EmployeeAlreadyCreatedException;
import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.users.UserClass;
import com.adamszablewski.users.clients.Client;
import com.adamszablewski.users.clients.repository.ClientRepository;
import com.adamszablewski.users.employee.Employee;
import com.adamszablewski.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.adamszablewski.dto.mapper.Mapper.mapClientToDto;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

//    public Client getClientByMail(String email) {
//        return clientRepository.findByEmail(email)
//                .orElseThrow(NoSuchUserException::new);
//    }

    public ClientDto getClientById(long id) {
        return mapClientToDto(clientRepository.findById(id)
                .orElseThrow(NoSuchUserException::new));
    }

    public void makeClientFromUser(long id) {

        UserClass user = userRepository.findById(id)
                .orElseThrow(NoSuchUserException::new);
        if (user.getClient() != null){
            throw new ClientAlreadyCreatedException();
        }

        Client newClient = Client.builder()
                    .user(user)
                    .build();
        user.setClient(newClient);
        clientRepository.save(newClient);

    }

//    UserClass user = userRepository.findById(id)
//            .orElseThrow(NoSuchUserException::new);
//        if (user.getEmployee() != null){
//        throw new EmployeeAlreadyCreatedException();
//    }
//
//    Employee newEmployee = Employee.builder()
//            .user(user)
//            .build();
//        user.setEmployee(newEmployee);
//        employeeRepository.save(newEmployee);
}
