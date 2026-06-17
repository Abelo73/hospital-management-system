package com.act.hospitalmanagementsystem.admin.dto;

import com.act.hospitalmanagementsystem.admin.enums.ConfigType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SystemConfigDTO {
    private UUID id;
    private String configKey;
    private String configValue;
    private String defaultValue;
    private ConfigType configType;
    private String description;
    private String category;
    private Boolean isEditable;
    private Boolean requiresRestart;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
