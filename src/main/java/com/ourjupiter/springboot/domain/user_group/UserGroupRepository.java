package com.ourjupiter.springboot.domain.user_group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    @Query("SELECT ug FROM UserGroup ug where ug.id.userId = :#{#userId} and ug.joined = 1")
    List<UserGroup> findJoinedGroupByUserId(Long userId);

    @Query("SELECT ug FROM UserGroup ug where ug.id.userId = :#{#userId} and ug.id.groupId = :#{#groupId}")
    Optional<UserGroup> findByIds(Long userId, Long groupId);

    @Query("SELECT ug FROM UserGroup ug where ug.id.groupId = :#{#groupId} and ug.joined = 1")
    List<UserGroup> findByGroupId(Long groupId);

    @Query("SELECT ug FROM UserGroup ug ORDER BY ug.id DESC")
    List<UserGroup> findAllDesc();
}
