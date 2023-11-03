package com.adamszablewski.repository;

import com.adamszablewski.model.Task;
import org.springframework.data.domain.Page;
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

   // Set<Task> findByCityAndCategory(String city, String category);

    @Query("Select t FROM Task t where t.city = :city ORDER BY t.points DESC")
    Page<Task> findByCityTop(@Param("city") String city, Pageable pageable);
    @Query("SELECT t FROM Task t JOIN t.category c WHERE t.city = :city AND c.name = :cname ORDER BY t.points DESC")
    Page<Task> findByCityTopAndCategoryTop(@Param("city") String city, @Param("cname") String cname, Pageable pageable);
    @Query("Select t FROM Task t where t.city = :city AND t.name LIKE :text% ORDER BY t.points DESC")
    Page<Task> findByCityAndNameTop(@Param("city") String city, @Param("text") String text, Pageable pageable);

}
