package com.example.dashboard.repository;

import com.example.dashboard.entity.AcademicPerformance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AcademicPerformanceRepository extends JpaRepository<AcademicPerformance, UUID> {
    List<AcademicPerformance> findByStudentIdAndSemester(UUID studentId, String semester);
    List<AcademicPerformance> findByStudentId(UUID studentId);
}
