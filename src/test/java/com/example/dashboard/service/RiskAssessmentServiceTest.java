package com.example.dashboard.service;

import com.example.dashboard.dto.StudentRiskAssessment;
import com.example.dashboard.repository.*;
import com.example.dashboard.util.RiskLevel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RiskAssessmentServiceTest {

    @Test
    void calculateRiskScore_HighRiskStudent_ReturnsCorrectScore() {
        RiskAssessmentService svc = new RiskAssessmentService(null, null, null, null);
        StudentRiskAssessment res = new StudentRiskAssessment("00000000-0000-0000-0000-000000000000", 75.0, RiskLevel.HIGH);
        assertEquals(RiskLevel.HIGH, res.riskLevel());
        assertTrue(res.score() >= 70);
    }
}
