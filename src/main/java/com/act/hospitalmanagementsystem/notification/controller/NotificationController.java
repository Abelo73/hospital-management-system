package com.act.hospitalmanagementsystem.notification.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.notification.dto.NotificationDTO;
import com.act.hospitalmanagementsystem.notification.dto.SendNotificationRequest;
import com.act.hospitalmanagementsystem.notification.enums.NotificationStatus;
import com.act.hospitalmanagementsystem.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    @PreAuthorize("hasAuthority('NOTIFICATION_WRITE')")
    public ResponseEntity<BaseResponseDTO<NotificationDTO>> sendNotification(
            @Valid @RequestBody SendNotificationRequest request,
            Authentication authentication) {
        String createdBy = authentication.getName();
        BaseResponseDTO<NotificationDTO> response = notificationService.sendNotification(request, createdBy);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/send-bulk")
    @PreAuthorize("hasAuthority('NOTIFICATION_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> sendBulkNotification(
            @Valid @RequestBody SendNotificationRequest request,
            @RequestParam List<String> recipientIds,
            Authentication authentication) {
        String createdBy = authentication.getName();
        BaseResponseDTO<Void> response = notificationService.sendBulkNotification(request, recipientIds, createdBy);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/status")
    @PreAuthorize("hasAuthority('NOTIFICATION_READ')")
    public ResponseEntity<BaseResponseDTO<NotificationDTO>> getNotificationStatus(@PathVariable UUID id) {
        BaseResponseDTO<NotificationDTO> response = notificationService.getNotificationStatus(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('NOTIFICATION_READ')")
    public ResponseEntity<BaseResponseDTO<List<NotificationDTO>>> getUserNotifications(
            @RequestParam(required = false) String recipientId,
            @RequestParam(required = false) NotificationStatus status,
            Pageable pageable) {
        BaseResponseDTO<List<NotificationDTO>> response = notificationService.getUserNotifications(
                recipientId, status, pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/read")
    @PreAuthorize("hasAuthority('NOTIFICATION_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> markAsRead(@PathVariable UUID id) {
        BaseResponseDTO<Void> response = notificationService.markAsRead(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/read-all")
    @PreAuthorize("hasAuthority('NOTIFICATION_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> markAllAsRead(Authentication authentication) {
        // TODO: Implement mark all as read functionality
        return ResponseEntity.ok(BaseResponseDTO.success(null, "Mark all as read not yet implemented"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('NOTIFICATION_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteNotification(@PathVariable UUID id) {
        BaseResponseDTO<Void> response = notificationService.deleteNotification(id);
        return ResponseEntity.ok(response);
    }
}
