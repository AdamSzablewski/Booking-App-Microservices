package com.adamszablewski.facilities.employmentRequests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmploymentRequestRepository extends JpaRepository<EmploymentRequest, Long> {

}
