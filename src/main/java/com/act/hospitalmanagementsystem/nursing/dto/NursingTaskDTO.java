package com.act.hospitalmanagementsystem.nursing.dto;

import com.act.hospitalmanagementsystem.nursing.enums.TaskCategory;
import com.act.hospitalmanagementsystem.nursing.enums.TaskPriority;
import com.act.hospitalmanagementsystem.nursing.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NursingTaskDTO {
    private UUID id;
    private UUID patientId;
    private UUID admissionId;
    private String taskTitle;
    private String taskDescription;
    private TaskCategory taskCategory;
    private TaskPriority priority;
    private LocalDate scheduledDate;
    private LocalTime scheduledTime;
    private UUID assignedTo;
    private UUID assignedBy;
    private TaskStatus status;
    private LocalDate completedDate;
    private LocalTime completedTime;
    private UUID completedBy;
    private String completionNotes;
    private Boolean isRecurring;
    private String recurringPattern;
    private LocalDate recurringEndDate;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private UUID updatedBy;
    private LocalDateTime updatedAt;
}
