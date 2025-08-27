package com.example.dashboard.service;

import com.example.dashboard.dto.AtRiskStudent;
import com.example.dashboard.util.RiskLevel;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

@Service
public class BulkRiskAssessmentService {

    private final RiskAssessmentService riskService;

    public BulkRiskAssessmentService(RiskAssessmentService riskService) {
        this.riskService = riskService;
    }

    @Async
    public CompletableFuture<String> assessClassRisk(String teacherId, String semester) {
        try {
            var page = riskService.identifyAtRiskStudents(semester, RiskLevel.LOW, 0, 1000);
            Path tmp = Files.createTempFile("risk-report-", ".csv");
            try (FileWriter fw = new FileWriter(tmp.toFile())) {
                fw.write("studentId,name,score,riskLevel\n");
                for (AtRiskStudent s : page.getContent()) {
                    fw.write(String.join(",", s.studentId(), s.name(), String.valueOf(s.score()), s.riskLevel().name()));
                    fw.write("\n");
                }
            }
            return CompletableFuture.completedFuture(tmp.toAbsolutePath().toString());
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    public void generateRiskReport(String teacherId, String semester) {
        assessClassRisk(teacherId, semester);
    }
}
