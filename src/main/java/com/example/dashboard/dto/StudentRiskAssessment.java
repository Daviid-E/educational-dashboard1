package com.example.dashboard.dto;

import com.example.dashboard.util.RiskLevel;

public record StudentRiskAssessment(String studentId, double score, RiskLevel riskLevel) {}
