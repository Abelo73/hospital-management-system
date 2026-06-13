package com.act.hospitalmanagementsystem.notification.dto;

import com.act.hospitalmanagementsystem.notification.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceDTO {
    private UUID id;
    private UUID userId;
    private NotificationType notificationType;
    private Boolean emailEnabled;
    private Boolean smsEnabled;
    private Boolean pushEnabled;
    private Boolean inAppEnabled;
    private LocalTime quietHoursStart;
    private LocalTime quietHoursEnd;
    private Boolean quietHoursEnabled;
    private String frequency;
}
