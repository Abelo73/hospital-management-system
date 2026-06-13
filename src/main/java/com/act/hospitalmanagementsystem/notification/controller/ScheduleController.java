package com.act.hospitalmanagementsystem.notification.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.notification.dto.ScheduleDTO;
import com.act.hospitalmanagementsystem.notification.enums.NotificationType;
import com.act.hospitalmanagementsystem.notification.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    @PreAuthorize("hasAuthority('NOTIFICATION_READ')")
    public ResponseEntity<BaseResponseDTO<List<ScheduleDTO>>> getSchedules(
            @RequestParam(required = false) Boolean isActive) {
        BaseResponseDTO<List<ScheduleDTO>> response = scheduleService.getSchedules(isActive);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('NOTIFICATION_ADMIN')")
    public ResponseEntity<BaseResponseDTO<ScheduleDTO>> createSchedule(
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        String scheduleName = (String) request.get("scheduleName");
        NotificationType notificationType = NotificationType.valueOf((String) request.get("notificationType"));
        UUID templateId = request.get("templateId") != null ? UUID.fromString((String) request.get("templateId")) : null;
        Map<String, Object> recipients = (Map<String, Object>) request.get("recipients");
        String cronExpression = (String) request.get("cronExpression");
        String timezone = (String) request.get("timezone");
        String createdBy = authentication.getName();

        BaseResponseDTO<ScheduleDTO> response = scheduleService.createSchedule(
                scheduleName, notificationType, templateId, recipients, cronExpression, timezone, createdBy);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('NOTIFICATION_ADMIN')")
    public ResponseEntity<BaseResponseDTO<ScheduleDTO>> updateSchedule(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        Boolean isActive = (Boolean) request.get("isActive");
        String updatedBy = authentication.getName();

        BaseResponseDTO<ScheduleDTO> response = scheduleService.updateSchedule(id, isActive, updatedBy);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/trigger")
    @PreAuthorize("hasAuthority('NOTIFICATION_ADMIN')")
    public ResponseEntity<BaseResponseDTO<Void>> triggerScheduleManually(@PathVariable UUID id) {
        BaseResponseDTO<Void> response = scheduleService.triggerScheduleManually(id);
        return ResponseEntity.ok(response);
    }
}
