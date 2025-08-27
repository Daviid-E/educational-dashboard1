package com.example.dashboard.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "behavior")
public class Behavior {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private UUID studentId;

    @Column(length = 20)
    private String semester;

    @Column(nullable = false)
    private Integer disciplinaryActions = 0;

    @Column(nullable = false)
    private Integer suspensions = 0;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getStudentId() { return studentId; }
    public void setStudentId(UUID studentId) { this.studentId = studentId; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public Integer getDisciplinaryActions() { return disciplinaryActions; }
    public void setDisciplinaryActions(Integer disciplinaryActions) { this.disciplinaryActions = disciplinaryActions; }

    public Integer getSuspensions() { return suspensions; }
    public void setSuspensions(Integer suspensions) { this.suspensions = suspensions; }
}
