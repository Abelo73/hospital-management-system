package com.act.hospitalmanagementsystem.nursing.dto;

import com.act.hospitalmanagementsystem.nursing.enums.TaskCategory;
import com.act.hospitalmanagementsystem.nursing.enums.TaskPriority;
import com.act.hospitalmanagementsystem.nursing.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNursingTaskRequest {
    private UUID admissionId;

    @NotBlank(message = "Task title is required")
    @Size(max = 200, message = "Task title must not exceed 200 characters")
    private String taskTitle;

    @Size(max = 5000, message = "Task description must not exceed 5000 characters")
    private String taskDescription;

    private TaskCategory taskCategory;

    private TaskPriority priority;

    private LocalDate scheduledDate;

    private LocalTime scheduledTime;

    private UUID assignedTo;

    private UUID assignedBy;

    @NotNull(message = "Status is required")
    private TaskStatus status;

    private LocalDate completedDate;

    private LocalTime completedTime;

    private UUID completedBy;

    @Size(max = 5000, message = "Completion notes must not exceed 5000 characters")
    private String completionNotes;

    private Boolean isRecurring;

    @Size(max = 200, message = "Recurring pattern must not exceed 200 characters")
    private String recurringPattern;

    private LocalDate recurringEndDate;
}
