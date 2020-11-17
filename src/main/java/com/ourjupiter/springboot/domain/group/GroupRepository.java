package com.ourjupiter.springboot.domain.group;

import com.ourjupiter.springboot.domain.posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("SELECT g FROM Group g where g.ownerId = :#{#userId}")
    List<Group> findAllByOwnerId(Long userId);

    @Query("SELECT g FROM Group g ORDER BY g.id DESC")
    List<Posts> findAllDesc();
}