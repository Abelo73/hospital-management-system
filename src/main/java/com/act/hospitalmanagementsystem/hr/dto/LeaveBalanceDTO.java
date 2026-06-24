package com.act.hospitalmanagementsystem.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveBalanceDTO {
    private UUID id;
    private UUID employeeId;
    private UUID leaveTypeId;
    private String leaveTypeName;
    private String leaveTypeCode;
    private Integer leaveCycleYear;
    private Integer entitledDays;
    private Integer usedDays;
    private Integer carriedForwardDays;
    private Integer remainingDays;
    private LocalDateTime createdAt;
    private String createdBy;
}
