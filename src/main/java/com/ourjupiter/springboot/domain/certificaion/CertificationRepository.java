package com.ourjupiter.springboot.domain.certificaion;

import com.ourjupiter.springboot.domain.goal.Goal;
import com.ourjupiter.springboot.domain.user_group.UserGroup;
import javafx.util.Pair;
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

    @Query("SELECT c.goal.user.name, c.fileId, c.todayDate FROM Certification c where c.goal.group.id= :#{#groupId}")
    List<Object[]> findByDaily(Long groupId);

    List<Certification> findByGoal_Group_IdAndGoal_Id_UserId(Long groupId, Long userId);

    //List<Certification> findByGoal_Group_Id(Long groupId);

    @Query("SELECT c FROM Certification c where c.goal.group.id = :#{#groupId}")
    List<Certification> findByGroupId(Long groupId);
}
