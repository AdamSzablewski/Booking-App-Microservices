package com.adamszablewski.repository;

import com.adamszablewski.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Set<Employee> findAllByTasks_NameAndWorkplace_Name(String taskName, String facilityName);
    //@Query("SELECT emp FROM Employee WHERE emp.id IN :ids")
    Set<Employee> findByIdIn(List<Long> ids);

    Optional<Employee> findByUserId(long id);

    // Optional<Employee> findByEmail(String email);
}
