package com.ourjupiter.springboot.domain.user_group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    @Query("SELECT ug FROM UserGroup ug ORDER BY ug.id DESC")
    List<UserGroup> findAllDesc();
}
