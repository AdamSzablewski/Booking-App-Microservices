package com.adamszablewski.feignClients.classes;


import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.tasks.Task;
import com.adamszablewski.util.Identifiable;
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
    //@JsonIgnoreProperties({"employees", "employee"})
    @JsonBackReference
    private Facility workplace;
    @OneToOne
    private UserClass user;
    private LocalTime startTime;
    private LocalTime endTime;
    @ManyToMany
    @JoinTable(
            name = "employee_tasks",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    @JsonIgnoreProperties({"employees", "employee"})
    @ToString.Exclude
    @JsonBackReference
    private List<Task> tasks;
    @OneToMany
    @JsonIgnoreProperties({"employees", "employee"})
    private List<Appointment> appointments;

    @Override
    public Long getId() {
        return id;
    }
}