package com.example.dashboard.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private UUID studentId;

    @Column(length = 20)
    private String semester;

    private BigDecimal attendanceRate; // DECIMAL(5,2)
    private Integer absentDays;
    private Integer tardyDays;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getStudentId() { return studentId; }
    public void setStudentId(UUID studentId) { this.studentId = studentId; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public BigDecimal getAttendanceRate() { return attendanceRate; }
    public void setAttendanceRate(BigDecimal attendanceRate) { this.attendanceRate = attendanceRate; }

    public Integer getAbsentDays() { return absentDays; }
    public void setAbsentDays(Integer absentDays) { this.absentDays = absentDays; }

    public Integer getTardyDays() { return tardyDays; }
    public void setTardyDays(Integer tardyDays) { this.tardyDays = tardyDays; }
}
