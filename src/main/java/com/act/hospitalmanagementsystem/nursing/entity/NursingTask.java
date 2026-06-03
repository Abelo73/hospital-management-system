package com.act.hospitalmanagementsystem.nursing.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.nursing.enums.TaskCategory;
import com.act.hospitalmanagementsystem.nursing.enums.TaskPriority;
import com.act.hospitalmanagementsystem.nursing.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "nursing_tasks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NursingTask extends BaseEntity {

    @Column(name = "patient_id")
    private UUID patientId;

    @Column(name = "admission_id")
    private UUID admissionId;

    @Column(name = "task_title", nullable = false, length = 200)
    private String taskTitle;

    @Column(name = "task_description", columnDefinition = "TEXT")
    private String taskDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_category", length = 50)
    private TaskCategory taskCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", length = 50)
    private TaskPriority priority;

    @Column(name = "scheduled_date")
    private LocalDate scheduledDate;

    @Column(name = "scheduled_time")
    private LocalTime scheduledTime;

    @Column(name = "assigned_to")
    private UUID assignedTo;

    @Column(name = "assigned_by")
    private UUID assignedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private TaskStatus status;

    @Column(name = "completed_date")
    private LocalDate completedDate;

    @Column(name = "completed_time")
    private LocalTime completedTime;

    @Column(name = "completed_by")
    private UUID completedBy;

    @Column(name = "completion_notes", columnDefinition = "TEXT")
    private String completionNotes;

    @Column(name = "is_recurring")
    private Boolean isRecurring;

    @Column(name = "recurring_pattern", length = 200)
    private String recurringPattern;

    @Column(name = "recurring_end_date")
    private LocalDate recurringEndDate;
}
