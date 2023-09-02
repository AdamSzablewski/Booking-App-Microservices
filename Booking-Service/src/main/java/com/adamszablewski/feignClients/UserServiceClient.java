package com.adamszablewski.feignClients;

import com.adamszablewski.feignClients.classes.Client;
import com.adamszablewski.feignClients.classes.Employee;
import com.adamszablewski.feignClients.classes.Owner;
import com.adamszablewski.feignClients.classes.UserClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {
    @PutMapping("/users/saveAll")
    void saveAllUsers(List<UserClass> users);

    @PutMapping("/users/saveAll")
    void save(UserClass users);

    @GetMapping("/users/employee/id/{id}")
    Optional<Employee> getEmployeeById(@PathVariable long id);

    @GetMapping("/users/clients/id/{id}")
    Optional<Client> getClientById(@PathVariable long id);

    @GetMapping("/users/owner/id/{id}")
    Optional<Owner> findOwnerById(@PathVariable long id);
    @GetMapping("/users/employee/email")
    Optional<Employee> findEmployeeByEmail(@PathVariable String email);
    @GetMapping("/users/employee")
    List<UserClass> findAllEmployeesByTaskAndFacility(@RequestParam("taskName") String taskName,
                                                      @RequestParam("facilityName") String facilityName);

    @PostMapping("/users/employee/ids")
    List<Employee> findEmployeesForIds(List<Long> employees);
}
