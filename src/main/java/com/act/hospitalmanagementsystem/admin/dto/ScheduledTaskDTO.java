package com.act.hospitalmanagementsystem.admin.dto;

import com.act.hospitalmanagementsystem.admin.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ScheduledTaskDTO {
    private UUID id;
    private String taskName;
    private String taskType;
    private String cronExpression;
    private String description;
    private TaskStatus status;
    private LocalDateTime lastRunAt;
    private LocalDateTime nextRunAt;
    private String lastRunStatus;
    private Long lastRunDuration;
    private String lastRunMessage;
    private Integer failureCount;
    private Boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
