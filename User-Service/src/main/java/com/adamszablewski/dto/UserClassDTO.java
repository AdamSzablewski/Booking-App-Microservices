package com.adamszablewski.dto;


import com.adamszablewski.Identifiable;
import com.adamszablewski.users.clients.Client;
import com.adamszablewski.users.employee.Employee;
import com.adamszablewski.users.owners.Owner;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Builder
public class UserClassDTO{
    private long id;
    private String firstName;
    private String lastName;
    private String country;
    private String region;
    private String city;
    private String email;
    private String phoneNumber;
    private EmployeeDto employee;
    private OwnerDto owner;
    private ClientDto client;
    @JsonIgnore
    private String password;

}
