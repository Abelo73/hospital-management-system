package com.act.hospitalmanagementsystem.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryGradeDTO {
    private UUID id;
    private String name;
    private String code;
    private Double minSalary;
    private Double maxSalary;
    private String currency;
    private LocalDateTime createdAt;
    private String createdBy;
}
