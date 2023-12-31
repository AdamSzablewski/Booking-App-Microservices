package com.adamszablewski.model;


import com.adamszablewski.util.Identifiable;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class Employee implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @ToString.Exclude
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
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Appointment> appointments;
    @OneToOne
    @ToString.Exclude
    private UserClass user;

    @Override
    public Long getId() {
        return id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

}