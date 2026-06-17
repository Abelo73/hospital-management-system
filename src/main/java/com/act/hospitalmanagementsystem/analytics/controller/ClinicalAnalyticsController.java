package com.act.hospitalmanagementsystem.analytics.controller;

import com.act.hospitalmanagementsystem.analytics.service.ClinicalAnalyticsService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analytics/clinical")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ANALYTICS_READ')")
public class ClinicalAnalyticsController {

    private final ClinicalAnalyticsService clinicalAnalyticsService;

    @GetMapping("/diagnoses/top")
    public ResponseEntity<BaseResponseDTO<List<Map<String, Object>>>> getTopDiagnoses(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(BaseResponseDTO.success(clinicalAnalyticsService.getTopDiagnoses(limit)));
    }

    @GetMapping("/consultations/by-doctor")
    public ResponseEntity<BaseResponseDTO<List<Map<String, Object>>>> getConsultationsByDoctor(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(BaseResponseDTO.success(clinicalAnalyticsService.getConsultationsByDoctor(limit)));
    }

    @GetMapping("/lab/stats")
    public ResponseEntity<BaseResponseDTO<Map<String, Object>>> getLabStats() {
        return ResponseEntity.ok(BaseResponseDTO.success(clinicalAnalyticsService.getLabTestStats()));
    }

    @GetMapping("/lab/top-tests")
    public ResponseEntity<BaseResponseDTO<List<Map<String, Object>>>> getTopLabTests(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(BaseResponseDTO.success(clinicalAnalyticsService.getTopLabTests(limit)));
    }
}
