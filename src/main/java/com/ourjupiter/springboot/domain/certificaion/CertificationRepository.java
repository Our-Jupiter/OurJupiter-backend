package com.ourjupiter.springboot.domain.certificaion;

import com.ourjupiter.springboot.domain.goal.Goal;
import com.ourjupiter.springboot.domain.user_group.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CertificationRepository extends JpaRepository<Certification, Long>{
    @Query("SELECT c FROM Certification c ORDER BY c.id DESC")
    List<Certification> findAllDesc();

    /*@Query("SELECT ug FROM UserGroup ug where ug.id.userId = :#{#userId} and ug.id.groupId = :#{#groupId}")*/
    /*Optional<UserGroup> findByIds(Long userId, Long groupId);*/

    //List<Certification> findByDailyCheck(Boolean dailyCheck);

    List<Certification> findByGoal_Group_IdAndGoal_Id_UserId(Long groupId, Long userId);

    @Query("SELECT c FROM Certification c where c.goal.user.id = :#{#userId} and c.goal.group.id = :#{#groupId}")
    Optional<Certification> findByIds(Long userId, Long groupId);
}
