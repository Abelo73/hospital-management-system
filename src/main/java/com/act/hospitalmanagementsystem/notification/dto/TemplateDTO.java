package com.act.hospitalmanagementsystem.notification.dto;

import com.act.hospitalmanagementsystem.notification.enums.NotificationType;
import com.act.hospitalmanagementsystem.notification.enums.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDTO {
    private UUID id;
    private String templateCode;
    private String templateName;
    private TemplateType templateType;
    private NotificationType notificationType;
    private String subject;
    private String body;
    private String variables;
    private String language;
    private Boolean isActive;
    private Integer version;
    private String description;
    private LocalDateTime createdAt;
    private String createdBy;
}
