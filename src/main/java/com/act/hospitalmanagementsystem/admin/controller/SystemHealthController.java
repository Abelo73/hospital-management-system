package com.act.hospitalmanagementsystem.admin.controller;

import com.act.hospitalmanagementsystem.admin.dto.SystemHealthDTO;
import com.act.hospitalmanagementsystem.admin.service.SystemHealthService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/health")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN_READ')")
public class SystemHealthController {

    private final SystemHealthService systemHealthService;

    @GetMapping
    public ResponseEntity<BaseResponseDTO<SystemHealthDTO>> getHealth() {
        return ResponseEntity.ok(BaseResponseDTO.success(systemHealthService.getSystemHealth()));
    }

    @GetMapping("/stats")
    public ResponseEntity<BaseResponseDTO<Map<String, Object>>> getStats() {
        return ResponseEntity.ok(BaseResponseDTO.success(systemHealthService.getSystemStats()));
    }
}
