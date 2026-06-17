package com.act.hospitalmanagementsystem.analytics.controller;

import com.act.hospitalmanagementsystem.analytics.service.OperationalAnalyticsService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analytics/operational")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ANALYTICS_READ')")
public class OperationalAnalyticsController {

    private final OperationalAnalyticsService operationalAnalyticsService;

    @GetMapping("/appointments/stats")
    public ResponseEntity<BaseResponseDTO<Map<String, Object>>> getAppointmentStats() {
        return ResponseEntity.ok(BaseResponseDTO.success(operationalAnalyticsService.getAppointmentStats()));
    }

    @GetMapping("/appointments/by-type")
    public ResponseEntity<BaseResponseDTO<List<Map<String, Object>>>> getByType() {
        return ResponseEntity.ok(BaseResponseDTO.success(operationalAnalyticsService.getAppointmentsByType()));
    }

    @GetMapping("/appointments/trend")
    public ResponseEntity<BaseResponseDTO<List<Map<String, Object>>>> getTrend() {
        return ResponseEntity.ok(BaseResponseDTO.success(operationalAnalyticsService.getMonthlyAppointmentTrend()));
    }

    @GetMapping("/doctors/top")
    public ResponseEntity<BaseResponseDTO<List<Map<String, Object>>>> getTopDoctors(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(BaseResponseDTO.success(operationalAnalyticsService.getBusyDoctors(limit)));
    }
}
