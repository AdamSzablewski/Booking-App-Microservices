package com.adamszablewski.facilities.employmentRequests;

import com.adamszablewski.facilities.Facility;
import com.adamszablewski.feignClients.classes.Employee;
import jakarta.persistence.*;
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
    @OneToOne
    private Employee employee;
    @OneToOne
    private Facility facility;
    private boolean status;
}
