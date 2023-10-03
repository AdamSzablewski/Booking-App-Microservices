package com.adamszablewski.feignClients;

import com.adamszablewski.dto.ClientDto;
import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.model.Employee;
import com.adamszablewski.model.Owner;
import com.adamszablewski.model.UserClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<RestResponseDTO<Employee>> getEmployeeById(@PathVariable long id);

    @GetMapping("/users/clients/id/{id}")
    RestResponseDTO<ClientDto> getClientById(@PathVariable long id);

    @GetMapping("/users/owner/id/{id}")
    Optional<Owner> findOwnerById(@PathVariable long id);
    @GetMapping("/users/employee/email/{email}")
    RestResponseDTO<Employee> findEmployeeByEmail(@PathVariable String email);
    @GetMapping("/users/employee")
    List<UserClass> findAllEmployeesByTaskAndFacility(@RequestParam("taskName") String taskName,
                                                      @RequestParam("facilityName") String facilityName);

    @PostMapping("/users/employee/ids")
    List<Employee> findEmployeesForIds(List<Long> employees);
    @GetMapping("/users/employee/id/{id}")
    RestResponseDTO<Employee> findEmployeeById(@PathVariable long id);
    @GetMapping("/users/owner/email/{email}")
    RestResponseDTO<Owner> findOwnerByEmail(@PathVariable String email);
    @GetMapping("/users/email/{email}")
    RestResponseDTO<UserClass> findUserByEmail(String ownerEmail);
}
