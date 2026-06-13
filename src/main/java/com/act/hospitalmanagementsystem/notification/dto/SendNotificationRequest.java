package com.act.hospitalmanagementsystem.notification.dto;

import com.act.hospitalmanagementsystem.notification.enums.NotificationChannel;
import com.act.hospitalmanagementsystem.notification.enums.NotificationType;
import com.act.hospitalmanagementsystem.notification.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendNotificationRequest {
    @NotNull(message = "Notification type is required")
    private NotificationType notificationType;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Message is required")
    private String message;

    @NotNull(message = "Channels are required")
    private List<NotificationChannel> channels;

    private Priority priority = Priority.MEDIUM;

    @NotBlank(message = "Recipient type is required")
    private String recipientType; // USER, PATIENT, ROLE, GROUP

    @NotBlank(message = "Recipient ID is required")
    private String recipientId;

    private String recipientEmail;

    private String recipientPhone;

    private UUID templateId;

    private Map<String, Object> variables;

    private LocalDateTime scheduledFor;
}
