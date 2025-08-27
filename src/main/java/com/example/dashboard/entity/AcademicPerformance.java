package com.example.dashboard.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "academic_performance")
public class AcademicPerformance {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private UUID studentId;

    @Column(length = 20)
    private String semester;

    @Column(length = 50)
    private String course;

    private BigDecimal grade; // DECIMAL(5,2)

    private Integer stateAssessmentEla;
    private Integer stateAssessmentMath;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getStudentId() { return studentId; }
    public void setStudentId(UUID studentId) { this.studentId = studentId; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public BigDecimal getGrade() { return grade; }
    public void setGrade(BigDecimal grade) { this.grade = grade; }

    public Integer getStateAssessmentEla() { return stateAssessmentEla; }
    public void setStateAssessmentEla(Integer stateAssessmentEla) { this.stateAssessmentEla = stateAssessmentEla; }

    public Integer getStateAssessmentMath() { return stateAssessmentMath; }
    public void setStateAssessmentMath(Integer stateAssessmentMath) { this.stateAssessmentMath = stateAssessmentMath; }
}
