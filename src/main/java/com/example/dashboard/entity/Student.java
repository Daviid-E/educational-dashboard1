package com.example.dashboard.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 10)
    private String grade;

    private LocalDateTime createdAt = LocalDateTime.now();

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
