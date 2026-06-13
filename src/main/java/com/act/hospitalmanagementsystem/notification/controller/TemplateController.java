package com.act.hospitalmanagementsystem.notification.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.notification.dto.CreateTemplateRequest;
import com.act.hospitalmanagementsystem.notification.dto.TemplateDTO;
import com.act.hospitalmanagementsystem.notification.service.TemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    @GetMapping
    @PreAuthorize("hasAuthority('NOTIFICATION_READ')")
    public ResponseEntity<BaseResponseDTO<List<TemplateDTO>>> getAllTemplates(Pageable pageable) {
        BaseResponseDTO<List<TemplateDTO>> response = templateService.getAllTemplates(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/code/{templateCode}")
    @PreAuthorize("hasAuthority('NOTIFICATION_READ')")
    public ResponseEntity<BaseResponseDTO<TemplateDTO>> getTemplateByCode(@PathVariable String templateCode) {
        BaseResponseDTO<TemplateDTO> response = templateService.getTemplateByCode(templateCode);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('NOTIFICATION_ADMIN')")
    public ResponseEntity<BaseResponseDTO<TemplateDTO>> createTemplate(
            @Valid @RequestBody CreateTemplateRequest request,
            Authentication authentication) {
        String createdBy = authentication.getName();
        BaseResponseDTO<TemplateDTO> response = templateService.createTemplate(request, createdBy);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('NOTIFICATION_ADMIN')")
    public ResponseEntity<BaseResponseDTO<TemplateDTO>> updateTemplate(
            @PathVariable UUID id,
            @RequestBody String body,
            Authentication authentication) {
        String updatedBy = authentication.getName();
        BaseResponseDTO<TemplateDTO> response = templateService.updateTemplate(id, body, updatedBy);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/test")
    @PreAuthorize("hasAuthority('NOTIFICATION_ADMIN')")
    public ResponseEntity<BaseResponseDTO<Void>> testTemplate(
            @PathVariable UUID id,
            @RequestParam String recipientEmail,
            @RequestBody Map<String, Object> variables) {
        BaseResponseDTO<Void> response = templateService.testTemplate(id, recipientEmail, variables);
        return ResponseEntity.ok(response);
    }
}
