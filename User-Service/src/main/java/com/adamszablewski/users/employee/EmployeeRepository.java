package com.adamszablewski.users.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByTasks_NameAndWorkplace_Name(String taskName, String facilityName);
    //@Query("SELECT emp FROM Employee WHERE emp.id IN :ids")
    List<Employee> findByIdIn(List<Long> ids);

   // Optional<Employee> findByEmail(String email);
}
