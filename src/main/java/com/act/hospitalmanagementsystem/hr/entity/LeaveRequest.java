package com.act.hospitalmanagementsystem.hr.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.hr.enums.LeaveStatus;
import com.act.hospitalmanagementsystem.hr.enums.LeaveType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "hr_leave_requests")
public class LeaveRequest extends BaseEntity {

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "leave_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "total_days", nullable = false)
    private Integer totalDays;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private LeaveStatus status = LeaveStatus.PENDING;

    @Column(name = "approved_by")
    private UUID approvedBy;

    @Column(name = "approved_on")
    private java.time.LocalDateTime approvedOn;

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;

    @Column(name = "attachment_url")
    private String attachmentUrl;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
