package com.act.hospitalmanagementsystem.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTaskRequest {
    @NotBlank(message = "Task name is required")
    private String taskName;
    @NotBlank(message = "Task type is required")
    private String taskType;
    private String cronExpression;
    private String description;
}
