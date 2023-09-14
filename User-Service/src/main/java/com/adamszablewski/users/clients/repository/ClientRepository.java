package com.adamszablewski.users.clients.repository;

import com.adamszablewski.users.clients.Client;
import com.adamszablewski.users.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

  //  Optional<Client> findByEmail(String email);
}
