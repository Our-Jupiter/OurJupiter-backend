package com.ourjupiter.springboot.domain.goal;

import com.ourjupiter.springboot.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    @Query("SELECT g FROM Goal g where g.id.groupId = :#{#groupId} and g.isExpired = 0")
    List<Goal> findActiveRoutine(Long groupId);

    @Query("SELECT g FROM Goal g where g.id.groupId = :#{#groupId}")
    List<Goal> findByGroupId(Long groupId);

    @Query("SELECT g FROM Goal g where g.id.userId = :#{#userId} and g.id.groupId = :#{#groupId} and g.isExpired = 0")
    Goal findActiveRoutineByIds(Long userId, Long groupId);

    @Query("SELECT g FROM Goal g ORDER BY g.id DESC")
    List<Goal> findAllDesc();
}
