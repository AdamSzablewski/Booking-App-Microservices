package com.adamszablewski.model;


import com.adamszablewski.Identifiable;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
@ToString
public class Employee implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Facility workplace;
    private LocalTime startTime;
    private LocalTime endTime;
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(
            name = "employee_tasks",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    @ToString.Exclude
    private Set<Task> tasks;
    @OneToMany(cascade = CascadeType.REMOVE)
    private Set<Appointment> appointments;
    @OneToOne
    @ToString.Exclude
    private UserClass user;

    @Override
    public Long getId() {
        return id;
    }
}