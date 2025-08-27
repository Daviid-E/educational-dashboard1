package com.example.dashboard.dto;

import com.example.dashboard.util.RiskLevel;

public record AtRiskStudent(String studentId, String name, double score, RiskLevel riskLevel) {}
