package com.adamszablewski.facilities;

import com.adamszablewski.services.Service;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Facility {

    @Id
    private BigInteger id;
    private String country;
    private String region;
    private String city;
    @OneToMany
    private List<Service> services;




}
