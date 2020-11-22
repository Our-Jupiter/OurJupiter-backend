package com.ourjupiter.springboot.domain.goal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    @Query("SELECT g FROM Goal g ORDER BY g.id DESC")
    List<Goal> findAllDesc();
}
