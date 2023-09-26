package com.adamszablewski.facilities.repository;

import com.adamszablewski.facilities.Facility;
import com.adamszablewski.tasks.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
    List<Facility> findByRegion(String region);
    List<Facility> findByCity(String city);

    Optional<Facility> findByName(String name);
    boolean existsByName(String name);

    Optional<Facility> findByOwnerId(long id);
}
