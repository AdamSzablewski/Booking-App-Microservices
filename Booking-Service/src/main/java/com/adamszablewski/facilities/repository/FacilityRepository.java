package com.adamszablewski.facilities.repository;

import com.adamszablewski.facilities.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, BigInteger> {
    List<Facility> findByRegion(String region);
    List<Facility> findByCity(String city);
}
