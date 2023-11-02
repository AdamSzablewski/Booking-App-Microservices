package com.adamszablewski.repository;

import com.adamszablewski.model.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByName(String name);
    Set<Task> findByCity(String city);
    Set<Task> findByCityAndCategory(String city, String category);
    @Query("Select t FROM Task where t.city = :city ORDER BY DESC")
    Set<Task> findByCityTop(@Param("city") String city, Pageable pageable);
    @Query("Select t FROM Task where t.city = :city AND t.category.name = :cname ORDER BY t.points DESC")
    Set<Task> findByCityTopAndCategory(@Param("city") String city, @Param("cname") String cname, Pageable pageable);
    @Query("Select t FROM Task where t.city = :city AND t.name LIKE :text% ORDER BY t.points DESC")
    Set<Task> findByCityAndNameTop(@Param("city") String city, Pageable pageable, @Param("text") String text);
}
