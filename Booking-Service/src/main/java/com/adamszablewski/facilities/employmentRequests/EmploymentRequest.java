package com.adamszablewski.facilities.employmentRequests;

import com.adamszablewski.facilities.Facility;
import com.adamszablewski.feignClients.classes.Employee;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private Employee employee;
    private Facility facility;
    private boolean status;
}
