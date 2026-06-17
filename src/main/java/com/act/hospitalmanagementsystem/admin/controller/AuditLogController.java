package com.act.hospitalmanagementsystem.admin.controller;

import com.act.hospitalmanagementsystem.admin.dto.AuditLogDTO;
import com.act.hospitalmanagementsystem.admin.service.AuditLogService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/audit-logs")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN_READ')")
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<BaseResponseDTO<Page<AuditLogDTO>>> getAuditLogs(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Pageable pageable) {
        return ResponseEntity.ok(BaseResponseDTO.success(
                auditLogService.getAuditLogs(userId, action, startDate, endDate, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDTO<AuditLogDTO>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(BaseResponseDTO.success(auditLogService.getById(id)));
    }

    @GetMapping("/export")
    public ResponseEntity<BaseResponseDTO<List<AuditLogDTO>>> exportLogs(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(BaseResponseDTO.success(auditLogService.exportLogs(startDate, endDate)));
    }
}
