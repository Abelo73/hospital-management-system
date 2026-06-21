package com.act.hospitalmanagementsystem.hr.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.HrDashboardDTO;
import com.act.hospitalmanagementsystem.hr.service.HrDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hr/dashboard")
@RequiredArgsConstructor
public class HrDashboardController {

    private final HrDashboardService hrDashboardService;

    @GetMapping("/kpis")
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<HrDashboardDTO>> getDashboard() {
        return ResponseEntity.ok(hrDashboardService.getDashboard());
    }
}
