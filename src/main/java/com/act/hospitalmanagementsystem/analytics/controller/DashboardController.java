package com.act.hospitalmanagementsystem.analytics.controller;

import com.act.hospitalmanagementsystem.analytics.service.DashboardService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/analytics/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ANALYTICS_READ')")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/executive")
    public ResponseEntity<BaseResponseDTO<Map<String, Object>>> getExecutive(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        LocalDate start = startDate != null ? startDate : LocalDate.now().withDayOfMonth(1);
        LocalDate end = endDate != null ? endDate : LocalDate.now();
        return ResponseEntity.ok(BaseResponseDTO.success(dashboardService.getExecutiveDashboard(start, end)));
    }

    @GetMapping("/operational")
    public ResponseEntity<BaseResponseDTO<Map<String, Object>>> getOperational(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(BaseResponseDTO.success(dashboardService.getOperationalDashboard(date != null ? date : LocalDate.now())));
    }

    @GetMapping("/financial")
    public ResponseEntity<BaseResponseDTO<Map<String, Object>>> getFinancial(
            @RequestParam(defaultValue = "0") int year) {
        return ResponseEntity.ok(BaseResponseDTO.success(dashboardService.getFinancialDashboard(year > 0 ? year : LocalDate.now().getYear())));
    }
}
