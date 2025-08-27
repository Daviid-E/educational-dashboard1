package com.example.dashboard.repository;

import com.example.dashboard.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> { }
