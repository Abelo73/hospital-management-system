package com.act.hospitalmanagementsystem.notification.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.notification.dto.PreferenceDTO;
import com.act.hospitalmanagementsystem.notification.dto.UpdatePreferenceRequest;
import com.act.hospitalmanagementsystem.notification.service.PreferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications/preferences")
@RequiredArgsConstructor
public class PreferenceController {

    private final PreferenceService preferenceService;

    @GetMapping
    @PreAuthorize("hasAuthority('NOTIFICATION_READ')")
    public ResponseEntity<BaseResponseDTO<List<PreferenceDTO>>> getUserPreferences(
            @RequestParam UUID userId) {
        BaseResponseDTO<List<PreferenceDTO>> response = preferenceService.getUserPreferences(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('NOTIFICATION_WRITE')")
    public ResponseEntity<BaseResponseDTO<PreferenceDTO>> updatePreference(
            @RequestParam UUID userId,
            @Valid @RequestBody UpdatePreferenceRequest request,
            Authentication authentication) {
        String updatedBy = authentication.getName();
        BaseResponseDTO<PreferenceDTO> response = preferenceService.updatePreference(userId, request, updatedBy);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/default")
    @PreAuthorize("hasAuthority('NOTIFICATION_READ')")
    public ResponseEntity<BaseResponseDTO<List<PreferenceDTO>>> getDefaultPreferences() {
        BaseResponseDTO<List<PreferenceDTO>> response = preferenceService.getDefaultPreferences();
        return ResponseEntity.ok(response);
    }
}
