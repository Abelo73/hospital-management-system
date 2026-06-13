package com.act.hospitalmanagementsystem.notification.dto;

import com.act.hospitalmanagementsystem.notification.enums.NotificationStatus;
import com.act.hospitalmanagementsystem.notification.enums.Priority;
import com.act.hospitalmanagementsystem.notification.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private UUID id;
    private NotificationType notificationType;
    private String title;
    private String message;
    private String channels;
    private Priority priority;
    private NotificationStatus status;
    private String recipientType;
    private String recipientId;
    private String recipientEmail;
    private String recipientPhone;
    private UUID templateId;
    private String variables;
    private LocalDateTime scheduledFor;
    private LocalDateTime sentAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime failedAt;
    private String failureReason;
    private Integer retryCount;
    private LocalDateTime createdAt;
    private String createdBy;
}
