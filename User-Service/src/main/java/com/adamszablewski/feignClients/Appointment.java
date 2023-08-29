package com.adamszablewski.feignClients;


import com.adamszablewski.users.clients.Client;
import com.adamszablewski.users.employee.Employee;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Appointment {
    @Id
    private BigInteger id;
    @ManyToOne
    private Facility facility;
    @ManyToOne
    private Task task;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String number;
    private String email;
    @OneToOne
    private Client client;
    @OneToOne
    private Employee employee;

}
