package com.adamszablewski.repository;

import com.adamszablewski.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
    Set<Facility> findByRegion(String region);
    Set<Facility> findByCity(String city);

    Optional<Facility> findByName(String name);
    boolean existsByName(String name);

    Optional<Facility> findByOwnerId(long id);
}
