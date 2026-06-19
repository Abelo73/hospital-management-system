package com.act.hospitalmanagementsystem.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    private UUID id;
    private String name;
    private String code;
    private UUID branchId;
    private String branchName;
    private UUID departmentHeadEmployeeId;
    private BigDecimal budget;
    private String description;
    private UUID parentDepartmentId;
    private String status;
    private Long employeeCount;
    private LocalDateTime createdAt;
    private String createdBy;
}
