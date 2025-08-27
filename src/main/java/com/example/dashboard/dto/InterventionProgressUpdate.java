package com.example.dashboard.dto;

import jakarta.validation.constraints.NotNull;

public record InterventionProgressUpdate(@NotNull double currentScore) {}
