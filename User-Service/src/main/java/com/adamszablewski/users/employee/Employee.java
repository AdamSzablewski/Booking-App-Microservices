package com.adamszablewski.users.employee;


import com.adamszablewski.feignClients.Appointment;
import com.adamszablewski.feignClients.Facility;
import com.adamszablewski.feignClients.Task;
import com.adamszablewski.users.UserClass;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class Employee extends UserClass {

    @ManyToOne
    private Facility workplace;
    private LocalTime startTime;
    private LocalTime endTime;
    @ManyToMany
    @JoinTable(
            name = "employee_tasks",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private List<Task> tasks;
    @OneToMany
    private List<Appointment> appointments;




}
