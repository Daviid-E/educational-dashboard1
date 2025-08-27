package com.example.dashboard.controller;

import com.example.dashboard.dto.AtRiskStudent;
import com.example.dashboard.dto.StudentRiskAssessment;
import com.example.dashboard.service.RiskAssessmentService;
import com.example.dashboard.util.RiskLevel;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risk-assessment")
public class RiskAssessmentController {

    private final RiskAssessmentService riskService;

    public RiskAssessmentController(RiskAssessmentService riskService) {
        this.riskService = riskService;
    }

    @GetMapping("/students/{studentId}")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public ResponseEntity<StudentRiskAssessment> getStudentRiskAssessment(
            @PathVariable String studentId,
            @RequestParam(required = false) String semester) {
        return ResponseEntity.ok(riskService.calculateRiskScore(studentId, semester));
    }

    @GetMapping("/at-risk")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public ResponseEntity<Page<AtRiskStudent>> getAtRiskStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String semester,
            @RequestParam(required = false) RiskLevel minimumRisk) {
        return ResponseEntity.ok(riskService.identifyAtRiskStudents(semester, minimumRisk, page, size));
    }
}
