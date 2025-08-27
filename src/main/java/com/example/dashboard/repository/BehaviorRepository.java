package com.example.dashboard.repository;

import com.example.dashboard.entity.Behavior;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BehaviorRepository extends JpaRepository<Behavior, UUID> {
    Optional<Behavior> findByStudentIdAndSemester(UUID studentId, String semester);
}
