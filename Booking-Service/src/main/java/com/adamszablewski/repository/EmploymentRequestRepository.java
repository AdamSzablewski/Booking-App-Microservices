package com.adamszablewski.repository;

import com.adamszablewski.model.EmploymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EmploymentRequestRepository extends JpaRepository<EmploymentRequest, Long> {



    Set<EmploymentRequest> findAllByEmployeeId(Long id);
}
