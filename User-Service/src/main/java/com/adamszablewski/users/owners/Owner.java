package com.adamszablewski.users.owners;


import com.adamszablewski.feignClients.Facility;
import com.adamszablewski.users.UserClass;
import com.adamszablewski.users.employee.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("owner")
    private List<Facility> facilities;
    @OneToOne
    private Employee employee;
    @OneToOne
    private UserClass user;

}
