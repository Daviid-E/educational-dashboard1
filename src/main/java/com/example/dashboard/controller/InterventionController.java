package com.example.dashboard.controller;

import com.example.dashboard.dto.CreateInterventionRequest;
import com.example.dashboard.dto.InterventionProgressUpdate;
import com.example.dashboard.dto.InterventionSummary;
import com.example.dashboard.entity.Intervention;
import com.example.dashboard.service.AuthorizationService;
import com.example.dashboard.service.InterventionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/interventions")
public class InterventionController {

    private final InterventionService interventionService;
    private final AuthorizationService authorizationService;

    public InterventionController(InterventionService interventionService, AuthorizationService authorizationService) {
        this.interventionService = interventionService;
        this.authorizationService = authorizationService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public ResponseEntity<Intervention> createIntervention(@Valid @RequestBody CreateInterventionRequest request) {
        return ResponseEntity.ok(interventionService.createIntervention(request));
    }

    @PutMapping("/{id}/progress")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public ResponseEntity<Intervention> updateProgress(@PathVariable String id, @Valid @RequestBody InterventionProgressUpdate update) {
        return ResponseEntity.ok(interventionService.updateInterventionProgress(id, update));
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN') or (hasRole('PARENT') and @authorizationService.canAccessStudentData(#studentId, authentication)) or (hasRole('STUDENT') and #studentId == authentication.name)")
    public ResponseEntity<List<Intervention>> getStudentInterventions(@PathVariable String studentId) {
        return ResponseEntity.ok(interventionService.getStudentInterventions(studentId));
    }

    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public ResponseEntity<InterventionSummary> summary(@RequestParam(required = false) String semester) {
        return ResponseEntity.ok(interventionService.getInterventionSummary(semester));
    }

    @GetMapping("/{id}/on-track")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public ResponseEntity<Boolean> isOnTrack(@PathVariable String id) {
        return ResponseEntity.ok(interventionService.isStudentOnTrack(id));
    }
}
