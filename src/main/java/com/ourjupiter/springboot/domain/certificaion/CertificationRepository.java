package com.ourjupiter.springboot.domain.certificaion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification, Long>{
    @Query("SELECT c FROM Certification c ORDER BY c.id DESC")
    List<Certification> findAllDesc();
}
