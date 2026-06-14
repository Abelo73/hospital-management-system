package com.act.hospitalmanagementsystem.hr.dto;

import com.act.hospitalmanagementsystem.hr.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
    private UUID id;
    private UUID employeeId;
    private LocalDate date;
    private String checkInTime;
    private String checkOutTime;
    private AttendanceStatus status;
    private Double hoursWorked;
    private Double overtimeHours;
    private String notes;
    private LocalDateTime createdAt;
    private String createdBy;
}
