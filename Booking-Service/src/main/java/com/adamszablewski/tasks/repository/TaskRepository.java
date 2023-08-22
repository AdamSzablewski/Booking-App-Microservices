package com.adamszablewski.tasks.repository;

import com.adamszablewski.tasks.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByName(String name);
    List<Task> findByRegionAndCity(String region, String city);
    List<Task> findByRegionAndCityAndCategory(String region, String city, String category);
}
