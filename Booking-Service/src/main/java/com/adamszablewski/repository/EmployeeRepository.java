package com.adamszablewski.repository;

import com.adamszablewski.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByTasks_NameAndWorkplace_Name(String taskName, String facilityName);
    //@Query("SELECT emp FROM Employee WHERE emp.id IN :ids")
    List<Employee> findByIdIn(List<Long> ids);

    Optional<Employee> findByUserId(long id);

    // Optional<Employee> findByEmail(String email);
}
