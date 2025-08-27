package com.example.dashboard.dto;
public record PredictionResult(String studentId, String subject, double predictedScore, double successLikelihood) {}
