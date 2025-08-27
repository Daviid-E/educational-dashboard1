package com.example.dashboard.service;

import com.example.dashboard.dto.AtRiskStudent;
import com.example.dashboard.dto.StudentRiskAssessment;
import com.example.dashboard.entity.*;
import com.example.dashboard.repository.*;
import com.example.dashboard.util.RiskLevel;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RiskAssessmentService {

    private final AcademicPerformanceRepository performanceRepo;
    private final AttendanceRepository attendanceRepo;
    private final BehaviorRepository behaviorRepo;
    private final StudentRepository studentRepo;

    public RiskAssessmentService(AcademicPerformanceRepository p, AttendanceRepository a, BehaviorRepository b, StudentRepository s) {
        this.performanceRepo = p;
        this.attendanceRepo = a;
        this.behaviorRepo = b;
        this.studentRepo = s;
    }

    public StudentRiskAssessment calculateRiskScore(String studentId, String semester) {
        UUID sid = UUID.fromString(studentId);
        double score = 0;

        List<AcademicPerformance> perf = (semester != null && !semester.isBlank())
                ? performanceRepo.findByStudentIdAndSemester(sid, semester)
                : performanceRepo.findByStudentId(sid);

        if (!perf.isEmpty()) {
            for (AcademicPerformance ap : perf) {
                if (ap.getGrade() != null && ap.getGrade().doubleValue() < 70) score += 25;
                if ((ap.getStateAssessmentEla() != null && ap.getStateAssessmentEla() < 500)
                        || (ap.getStateAssessmentMath() != null && ap.getStateAssessmentMath() < 500)) score += 15;
            }
        }

        double attendancePenalty = 0;
        Optional<Attendance> attOpt = (semester != null && !semester.isBlank())
                ? attendanceRepo.findByStudentIdAndSemester(sid, semester)
                : Optional.empty();

        if (attOpt.isPresent()) {
            var att = attOpt.get();
            if (att.getAttendanceRate() != null && att.getAttendanceRate().doubleValue() < 90) attendancePenalty += 20;
            if (att.getAbsentDays() != null && att.getAbsentDays() > 10) attendancePenalty += 10;
            if (att.getTardyDays() != null && att.getTardyDays() > 5) attendancePenalty += 10;
        }
        score += attendancePenalty;

        Optional<Behavior> behOpt = (semester != null && !semester.isBlank())
                ? behaviorRepo.findByStudentIdAndSemester(sid, semester)
                : Optional.empty();

        if (behOpt.isPresent()) {
            var beh = behOpt.get();
            if (beh.getDisciplinaryActions() != null && beh.getDisciplinaryActions() > 2) score += 15;
            if (beh.getSuspensions() != null && beh.getSuspensions() > 0) score += 5;
        }

        RiskLevel level = (score >= 70) ? RiskLevel.HIGH : (score >= 40 ? RiskLevel.MEDIUM : RiskLevel.LOW);
        return new StudentRiskAssessment(studentId, score, level);
    }

    public Page<AtRiskStudent> identifyAtRiskStudents(String semester, RiskLevel minimumRisk, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        var students = studentRepo.findAll(pageable);
        List<AtRiskStudent> list = new ArrayList<>();
        for (var s : students.getContent()) {
            var ra = calculateRiskScore(s.getId().toString(), semester);
            if (minimumRisk == null || ra.riskLevel().ordinal() >= minimumRisk.ordinal()) {
                list.add(new AtRiskStudent(s.getId().toString(), s.getName(), ra.score(), ra.riskLevel()));
            }
        }
        return new PageImpl<>(list, pageable, students.getTotalElements());
    }

    public void notifyStakeholders(List<AtRiskStudent> atRiskStudents) {
        // In real life: send emails/queue events. Here we log.
        atRiskStudents.forEach(s -> System.out.println("Notify: Student " + s.name() + " is " + s.riskLevel()));
    }
}
