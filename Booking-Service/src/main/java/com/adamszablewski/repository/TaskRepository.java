package com.adamszablewski.repository;

import com.adamszablewski.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByName(String name);
    Set<Task> findByCity(String city);
    Set<Task> findByCityAndCategory(String city, String category);
}
