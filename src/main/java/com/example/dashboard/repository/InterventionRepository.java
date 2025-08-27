package com.example.dashboard.repository;

import com.example.dashboard.entity.Intervention;
import com.example.dashboard.util.InterventionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InterventionRepository extends JpaRepository<Intervention, UUID> {
    List<Intervention> findByStudentId(UUID studentId);
    long countByStatus(InterventionStatus status);
}
