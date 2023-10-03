package com.adamszablewski.repository;

import com.adamszablewski.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

  //  Optional<Client> findByEmail(String email);
}
