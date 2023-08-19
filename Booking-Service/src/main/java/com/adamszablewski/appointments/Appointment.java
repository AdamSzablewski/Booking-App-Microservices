package com.adamszablewski.appointments;

import com.adamszablewski.facilities.Facility;
import com.adamszablewski.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Appointment {
    @Id
    private BigInteger id;
    @OneToOne
    private Facility facility;
    private User client;
    private User owner;
    private LocalDateTime dateTime;
}
