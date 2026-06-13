package com.act.hospitalmanagementsystem.notification.dto;

import com.act.hospitalmanagementsystem.notification.enums.NotificationType;
import com.act.hospitalmanagementsystem.notification.enums.TemplateType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTemplateRequest {
    @NotBlank(message = "Template code is required")
    private String templateCode;

    @NotBlank(message = "Template name is required")
    private String templateName;

    @NotNull(message = "Template type is required")
    private TemplateType templateType;

    @NotNull(message = "Notification type is required")
    private NotificationType notificationType;

    private String subject;

    @NotBlank(message = "Body is required")
    private String body;

    private List<String> variables;

    private String language = "en";

    private Boolean isActive = true;

    private String description;
}
