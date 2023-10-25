package com.adamszablewski.repository;

import com.adamszablewski.model.Client;
import com.adamszablewski.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

  //  Optional<Client> findByEmail(String email);
}
