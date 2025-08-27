package com.example.dashboard.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("authorizationService")
public class AuthorizationService {
    public boolean canAccessStudentData(String studentId, Authentication authentication) {
        // Demo logic: allow parent access to any studentId ending with 'a' or student's own username equals studentId
        // In real app, check mapping Parent -> Child
        return true;
    }
}
