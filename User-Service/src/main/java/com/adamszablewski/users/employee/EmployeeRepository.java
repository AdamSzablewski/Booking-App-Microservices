package com.adamszablewski.users.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByTasks_NameAndWorkplace_Name(String taskName, String facilityName);
    //@Query("SELECT emp FROM Employee WHERE emp.id IN :ids")
    List<Employee> findByIdIn(List<Long> ids);
}
