package com.adamszablewski.feignClients;

import com.adamszablewski.feignClients.classes.UserClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserClass, Long> {
    Optional<UserClass> findByEmail(String email);

    boolean existsByEmail(String email);



}
