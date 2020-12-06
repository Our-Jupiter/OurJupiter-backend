package com.ourjupiter.springboot.domain.goal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    @Query("SELECT g FROM Goal g where g.id.groupId = :#{#groupId} and g.isExpired = 0")
    List<Goal> findActiveRoutine(Long groupId);

    @Query("SELECT g FROM Goal g where g.id.groupId = :#{#groupId}")
    List<Goal> findByGroupId(Long groupId);

    @Query("SELECT g FROM Goal g where g.id.userId = :#{#userId} and g.id.groupId = :#{#groupId} and g.isExpired = 0")
    Goal findActiveRoutineByIds(Long userId, Long groupId);

    @Query("SELECT g FROM Goal g where g.id.groupId = :#{#groupId} and g.isExpired = 1")
    List<Goal> findGoalRecords(Long groupId);

    @Query("SELECT g FROM Goal g ORDER BY g.id DESC")
    List<Goal> findAllDesc();

    @Modifying
    @Query("UPDATE Goal g SET g.goal=:#{#goal}, g.penalty=:#{#penalty} WHERE g.id.userId = :#{#userId} and g.id.groupId = :#{#groupId} and g.isExpired = 0")
    Integer updateGoalPenalty(String goal, String penalty, Long userId, Long groupId);

    @Modifying
    @Query("UPDATE Goal g SET g.isExpired=:#{#isExpired} WHERE g.id.userId = :#{#userId} and g.id.groupId = :#{#groupId} and g.isExpired = 0")
    Integer updateIsExpired(Boolean isExpired, Long userId, Long groupId);

    @Modifying
    @Query("UPDATE Goal g SET g.doFeedback=:#{#doFeedback} WHERE g.id.userId = :#{#userId} and g.id.groupId = :#{#groupId} and g.isExpired = 0")
    Integer updateDoFeedback(Boolean doFeedback, Long userId, Long groupId);

    @Modifying
    @Query("UPDATE Goal g SET g.successNum=g.successNum + 1 WHERE g.id.userId = :#{#userId} and g.id.groupId = :#{#groupId} and g.isExpired = 0")
    Integer updateSuccessNum(Long userId, Long groupId);
}
