package com.example.dashboard.entity;

import com.example.dashboard.util.InterventionStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "interventions")
public class Intervention {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private UUID studentId;

    private String interventionType;
    private LocalDate startDate;
    private LocalDate targetCompletionDate;

    private BigDecimal startScore;
    private BigDecimal currentScore;
    private BigDecimal goalScore;

    @Enumerated(EnumType.STRING)
    private InterventionStatus status;

    private LocalDateTime createdAt = LocalDateTime.now();

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getStudentId() { return studentId; }
    public void setStudentId(UUID studentId) { this.studentId = studentId; }

    public String getInterventionType() { return interventionType; }
    public void setInterventionType(String interventionType) { this.interventionType = interventionType; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getTargetCompletionDate() { return targetCompletionDate; }
    public void setTargetCompletionDate(LocalDate targetCompletionDate) { this.targetCompletionDate = targetCompletionDate; }

    public BigDecimal getStartScore() { return startScore; }
    public void setStartScore(BigDecimal startScore) { this.startScore = startScore; }

    public BigDecimal getCurrentScore() { return currentScore; }
    public void setCurrentScore(BigDecimal currentScore) { this.currentScore = currentScore; }

    public BigDecimal getGoalScore() { return goalScore; }
    public void setGoalScore(BigDecimal goalScore) { this.goalScore = goalScore; }

    public InterventionStatus getStatus() { return status; }
    public void setStatus(InterventionStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
