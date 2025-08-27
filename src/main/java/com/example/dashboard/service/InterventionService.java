package com.example.dashboard.service;

import com.example.dashboard.dto.CreateInterventionRequest;
import com.example.dashboard.dto.InterventionProgressUpdate;
import com.example.dashboard.dto.InterventionSummary;
import com.example.dashboard.entity.Intervention;
import com.example.dashboard.repository.InterventionRepository;
import com.example.dashboard.util.InterventionStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class InterventionService {

    private final InterventionRepository interventionRepo;

    public InterventionService(InterventionRepository interventionRepo) {
        this.interventionRepo = interventionRepo;
    }

    @Transactional
    public Intervention createIntervention(CreateInterventionRequest request) {
        Intervention i = new Intervention();
        i.setStudentId(UUID.fromString(request.studentId()));
        i.setInterventionType(request.interventionType());
        i.setStartDate(request.startDate());
        i.setTargetCompletionDate(request.targetCompletionDate());
        i.setStartScore(BigDecimal.valueOf(request.startScore()));
        i.setCurrentScore(BigDecimal.valueOf(request.startScore()));
        i.setGoalScore(BigDecimal.valueOf(request.goalScore()));
        i.setStatus(InterventionStatus.ON_TRACK);
        return interventionRepo.save(i);
    }

    @Transactional
    public Intervention updateInterventionProgress(String interventionId, InterventionProgressUpdate update) {
        Intervention i = interventionRepo.findById(UUID.fromString(interventionId))
                .orElseThrow(() -> new IllegalArgumentException("Intervention not found"));
        i.setCurrentScore(BigDecimal.valueOf(update.currentScore()));
        // update status
        if (isGoalMet(i)) {
            i.setStatus(InterventionStatus.COMPLETED);
            // notify
            System.out.println("Notify: Intervention goal met for student " + i.getStudentId());
        } else {
            i.setStatus(isStudentOnTrack(i) ? InterventionStatus.ON_TRACK : InterventionStatus.NOT_ON_TRACK);
        }
        return interventionRepo.save(i);
    }

    public List<Intervention> getStudentInterventions(String studentId) {
        return interventionRepo.findByStudentId(UUID.fromString(studentId));
    }

    public InterventionSummary getInterventionSummary(String semester) {
        long total = interventionRepo.count();
        long on = interventionRepo.countByStatus(InterventionStatus.ON_TRACK);
        long not = interventionRepo.countByStatus(InterventionStatus.NOT_ON_TRACK);
        long done = interventionRepo.countByStatus(InterventionStatus.COMPLETED);
        return new InterventionSummary(total, on, not, done);
    }

    public boolean isStudentOnTrack(String interventionId) {
        Intervention i = interventionRepo.findById(UUID.fromString(interventionId))
                .orElseThrow(() -> new IllegalArgumentException("Intervention not found"));
        return isStudentOnTrack(i);
    }

    // ------- Helpers -------
    private boolean isGoalMet(Intervention i) {
        if (i.getCurrentScore() == null || i.getGoalScore() == null) return false;
        return i.getCurrentScore().doubleValue() >= i.getGoalScore().doubleValue();
    }

    private boolean isStudentOnTrack(Intervention i) {
        LocalDate start = i.getStartDate();
        LocalDate end = i.getTargetCompletionDate();
        if (start == null || end == null || i.getStartScore() == null || i.getGoalScore() == null || i.getCurrentScore() == null) {
            return false;
        }
        double totalDelta = i.getGoalScore().doubleValue() - i.getStartScore().doubleValue();
        if (totalDelta <= 0) return true; // already at/above goal

        long totalDays = Math.max(1, ChronoUnit.DAYS.between(start, end));
        long elapsedDays = Math.max(0, ChronoUnit.DAYS.between(start, LocalDate.now()));
        elapsedDays = Math.min(elapsedDays, totalDays);

        double expectedProgress = (elapsedDays / (double) totalDays) * totalDelta;
        double actualProgress = i.getCurrentScore().doubleValue() - i.getStartScore().doubleValue();

        // Consider on-track if actual >= 80% of expected (buffer)
        return actualProgress + 0.0001 >= 0.8 * expectedProgress;
    }
}
