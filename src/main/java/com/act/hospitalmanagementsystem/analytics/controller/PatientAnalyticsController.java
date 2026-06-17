package com.act.hospitalmanagementsystem.analytics.controller;

import com.act.hospitalmanagementsystem.analytics.service.PatientAnalyticsService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analytics/patient")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ANALYTICS_READ')")
public class PatientAnalyticsController {

    private final PatientAnalyticsService patientAnalyticsService;

    @GetMapping("/demographics/gender")
    public ResponseEntity<BaseResponseDTO<List<Map<String, Object>>>> getByGender() {
        return ResponseEntity.ok(BaseResponseDTO.success(patientAnalyticsService.getDemographicsByGender()));
    }

    @GetMapping("/demographics/status")
    public ResponseEntity<BaseResponseDTO<List<Map<String, Object>>>> getByStatus() {
        return ResponseEntity.ok(BaseResponseDTO.success(patientAnalyticsService.getDemographicsByStatus()));
    }

    @GetMapping("/growth")
    public ResponseEntity<BaseResponseDTO<Map<String, Object>>> getGrowth() {
        return ResponseEntity.ok(BaseResponseDTO.success(patientAnalyticsService.getPatientGrowth()));
    }

    @GetMapping("/stats")
    public ResponseEntity<BaseResponseDTO<Map<String, Object>>> getStats() {
        return ResponseEntity.ok(BaseResponseDTO.success(patientAnalyticsService.getRegistrationStats()));
    }
}
