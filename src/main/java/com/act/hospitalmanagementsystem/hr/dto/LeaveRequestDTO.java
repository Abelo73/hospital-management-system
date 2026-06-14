package com.act.hospitalmanagementsystem.hr.dto;

import com.act.hospitalmanagementsystem.hr.enums.LeaveStatus;
import com.act.hospitalmanagementsystem.hr.enums.LeaveType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequestDTO {
    private UUID id;
    private UUID employeeId;
    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer totalDays;
    private String reason;
    private LeaveStatus status;
    private UUID approvedBy;
    private LocalDateTime approvedOn;
    private String rejectionReason;
    private String attachmentUrl;
    private String notes;
    private LocalDateTime createdAt;
    private String createdBy;
}
