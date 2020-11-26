package com.ourjupiter.springboot.domain.goal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    @Query("SELECT g FROM Goal g where g.id.groupId = :#{#groupId} and g.is_expired = 0")
    List<Goal> findActiveRoutine(Long groupId);

    @Query(value = "SELECT g FROM Goal g where g.id.userId = :#{#userId} and g.id.groupId = :#{#groupId} and g.is_expired = 0")
    Goal findActiveRoutineByIds(Long userId, Long groupId);

    @Query("SELECT g FROM Goal g ORDER BY g.id DESC")
    List<Goal> findAllDesc();
}
