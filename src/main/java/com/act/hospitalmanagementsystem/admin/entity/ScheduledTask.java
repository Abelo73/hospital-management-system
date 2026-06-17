package com.act.hospitalmanagementsystem.admin.entity;

import com.act.hospitalmanagementsystem.admin.enums.TaskStatus;
import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "admin_scheduled_tasks")
public class ScheduledTask extends BaseEntity {

    @Column(name = "task_name", unique = true, nullable = false, length = 100)
    private String taskName;

    @Column(name = "task_type", length = 50, nullable = false)
    private String taskType;

    @Column(name = "cron_expression", length = 100)
    private String cronExpression;

    @Column(name = "description", length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private TaskStatus status = TaskStatus.ACTIVE;

    @Column(name = "last_run_at")
    private LocalDateTime lastRunAt;

    @Column(name = "next_run_at")
    private LocalDateTime nextRunAt;

    @Column(name = "last_run_status", length = 50)
    private String lastRunStatus;

    @Column(name = "last_run_duration")
    private Long lastRunDuration;

    @Column(name = "last_run_message", length = 1000)
    private String lastRunMessage;

    @Column(name = "failure_count", nullable = false)
    private Integer failureCount = 0;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;
}
