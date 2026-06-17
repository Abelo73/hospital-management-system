package com.act.hospitalmanagementsystem.admin.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AuditLogDTO {
    private UUID id;
    private UUID userId;
    private String username;
    private String action;
    private String entityType;
    private String entityId;
    private String oldValue;
    private String newValue;
    private String ipAddress;
    private String requestUrl;
    private String requestMethod;
    private Integer responseStatus;
    private Long duration;
    private String errorMessage;
    private LocalDateTime timestamp;
}
