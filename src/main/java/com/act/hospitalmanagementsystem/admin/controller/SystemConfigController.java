package com.act.hospitalmanagementsystem.admin.controller;

import com.act.hospitalmanagementsystem.admin.dto.SystemConfigDTO;
import com.act.hospitalmanagementsystem.admin.dto.UpdateConfigRequest;
import com.act.hospitalmanagementsystem.admin.service.SystemConfigService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/config")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN_READ')")
public class SystemConfigController {

    private final SystemConfigService systemConfigService;

    @GetMapping
    public ResponseEntity<BaseResponseDTO<Page<SystemConfigDTO>>> getAllConfigs(Pageable pageable) {
        return ResponseEntity.ok(BaseResponseDTO.success(systemConfigService.getAllConfigs(pageable)));
    }

    @GetMapping("/{configKey}")
    public ResponseEntity<BaseResponseDTO<SystemConfigDTO>> getConfig(@PathVariable String configKey) {
        return ResponseEntity.ok(BaseResponseDTO.success(systemConfigService.getByKey(configKey)));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<BaseResponseDTO<List<SystemConfigDTO>>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(BaseResponseDTO.success(systemConfigService.getByCategory(category)));
    }

    @PutMapping("/{configKey}")
    @PreAuthorize("hasAuthority('ADMIN_WRITE')")
    public ResponseEntity<BaseResponseDTO<SystemConfigDTO>> updateConfig(
            @PathVariable String configKey, @Valid @RequestBody UpdateConfigRequest request) {
        return ResponseEntity.ok(BaseResponseDTO.success("Configuration updated", systemConfigService.updateConfig(configKey, request)));
    }

    @PostMapping("/{configKey}/reset")
    @PreAuthorize("hasAuthority('ADMIN_WRITE')")
    public ResponseEntity<BaseResponseDTO<SystemConfigDTO>> resetConfig(@PathVariable String configKey) {
        return ResponseEntity.ok(BaseResponseDTO.success("Configuration reset to default", systemConfigService.resetToDefault(configKey)));
    }
}
