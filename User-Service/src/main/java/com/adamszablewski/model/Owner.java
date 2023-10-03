package com.adamszablewski.model;


import com.adamszablewski.Identifiable;
import jakarta.persistence.*;
import lombok.*;


import java.util.List;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Owner implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany
    private Set<Facility> facilities;
    @OneToOne
    @ToString.Exclude
    private UserClass user;

    @Override
    public Long getId() {
        return id;
    }
}
