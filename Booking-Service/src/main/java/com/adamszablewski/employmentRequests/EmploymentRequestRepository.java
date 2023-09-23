package com.adamszablewski.employmentRequests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmploymentRequestRepository extends JpaRepository<EmploymentRequest, Long> {



    List<EmploymentRequest> findAllByEmployeeId(Long id);
}
