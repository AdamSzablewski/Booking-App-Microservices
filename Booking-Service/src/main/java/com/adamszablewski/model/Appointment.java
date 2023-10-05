package com.adamszablewski.model;

import com.adamszablewski.util.Identifiable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Appointment implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Facility facility;
    @ManyToOne
    //@ToString.Exclude
    private Task task;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String number;
    private String email;
    @ManyToOne
    //@ToString.Exclude
    private Client client;
    @ManyToOne
   // @ToString.Exclude
    private Employee employee;

    @Override
    public Long getId() {
        return id;
    }
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Appointment other = (Appointment) obj;
        return Objects.equals(id, other.id) && Objects.equals(date, other.date);
    }

}
