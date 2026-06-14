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
public class TrainingDTO {
    private UUID id;
    private String trainingName;
    private String description;
    private String trainingType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private String instructor;
    private Double cost;
    private Integer maxParticipants;
    private String status;
    private String notes;
    private LocalDateTime createdAt;
    private String createdBy;
}
