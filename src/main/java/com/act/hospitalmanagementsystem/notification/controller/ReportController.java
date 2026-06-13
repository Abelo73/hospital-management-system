package com.act.hospitalmanagementsystem.notification.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.notification.service.TrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications/reports")
@RequiredArgsConstructor
public class ReportController {

    private final TrackingService trackingService;

    @PostMapping("/delivery")
    @PreAuthorize("hasAuthority('NOTIFICATION_READ')")
    public ResponseEntity<BaseResponseDTO<Map<String, Object>>> generateDeliveryReport(
            @RequestBody Map<String, String> request) {
        String startDate = request.get("startDate");
        String endDate = request.get("endDate");
        String notificationType = request.get("notificationType");
        String channel = request.get("channel");
        String format = request.get("format");

        BaseResponseDTO<Map<String, Object>> response = trackingService.generateDeliveryReport(
                startDate, endDate, notificationType, channel, format);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/analytics")
    @PreAuthorize("hasAuthority('NOTIFICATION_READ')")
    public ResponseEntity<BaseResponseDTO<Map<String, Object>>> generateAnalyticsReport(
            @RequestBody Map<String, Object> request) {
        String startDate = (String) request.get("startDate");
        String endDate = (String) request.get("endDate");
        List<String> metrics = (List<String>) request.get("metrics");

        BaseResponseDTO<Map<String, Object>> response = trackingService.generateAnalyticsReport(
                startDate, endDate, metrics);
        return ResponseEntity.ok(response);
    }
}
