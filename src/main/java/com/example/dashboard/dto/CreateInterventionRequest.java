package com.example.dashboard.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateInterventionRequest(
        @NotNull String studentId,
        @NotNull String interventionType,
        @NotNull LocalDate startDate,
        @NotNull LocalDate targetCompletionDate,
        double startScore,
        double goalScore
) {}
