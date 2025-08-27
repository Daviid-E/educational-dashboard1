package com.example.dashboard.repository;

import com.example.dashboard.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
    Optional<Attendance> findByStudentIdAndSemester(UUID studentId, String semester);
}
