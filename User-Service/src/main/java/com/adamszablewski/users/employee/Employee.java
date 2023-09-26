package com.adamszablewski.users.employee;


import com.adamszablewski.Identifiable;
import com.adamszablewski.feignClients.Appointment;
import com.adamszablewski.feignClients.Facility;
import com.adamszablewski.feignClients.Task;
import com.adamszablewski.users.UserClass;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
@ToString
public class Employee implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
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
    @ToString.Exclude
    private List<Task> tasks;
    @OneToMany
    private List<Appointment> appointments;
    @OneToOne
    private UserClass user;

    @Override
    public Long getId() {
        return id;
    }
}