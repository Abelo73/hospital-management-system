package com.act.hospitalmanagementsystem.notification.dto;

import com.act.hospitalmanagementsystem.notification.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private UUID id;
    private String scheduleName;
    private NotificationType notificationType;
    private UUID templateId;
    private String recipients;
    private String cronExpression;
    private String timezone;
    private Boolean isActive;
    private LocalDateTime lastRunAt;
    private LocalDateTime nextRunAt;
    private Integer runCount;
    private Integer failureCount;
    private LocalDateTime createdAt;
    private String createdBy;
}
