package com.act.hospitalmanagementsystem.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentDTO {
    private UUID id;
    private String jobTitle;
    private String department;
    private String description;
    private String requirements;
    private String responsibilities;
    private Integer vacancies;
    private LocalDate postingDate;
    private LocalDate closingDate;
    private String status;
    private String salaryRange;
    private String location;
    private String employmentType;
    private String notes;
    private LocalDateTime createdAt;
    private String createdBy;
}
