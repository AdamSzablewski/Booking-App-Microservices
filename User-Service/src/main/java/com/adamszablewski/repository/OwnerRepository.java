package com.adamszablewski.repository;

import com.adamszablewski.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

//    List<Owner> findAllByTasks_NameAndWorkplace_Name(String taskName, String facilityName);
//    //@Query("SELECT emp FROM Employee WHERE emp.id IN :ids")
//    List<Owner> findByIdIn(List<Long> ids);

   // Optional<Owner> findByEmail(String email);

    Optional<Owner> findByUserId(long id);
}
