package com.act.hospitalmanagementsystem.notification.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.notification.enums.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "notification_schedules")
public class NotificationSchedule extends BaseEntity {

    @Column(name = "schedule_name", nullable = false)
    private String scheduleName;

    @Column(name = "notification_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private Template template;

    @Column(name = "recipients", columnDefinition = "JSON")
    private String recipients;

    @Column(name = "cron_expression", nullable = false)
    private String cronExpression;

    @Column(name = "timezone", length = 50)
    private String timezone = "Africa/Nairobi";

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "last_run_at")
    private LocalDateTime lastRunAt;

    @Column(name = "next_run_at")
    private LocalDateTime nextRunAt;

    @Column(name = "run_count")
    private Integer runCount = 0;

    @Column(name = "failure_count")
    private Integer failureCount = 0;
}
