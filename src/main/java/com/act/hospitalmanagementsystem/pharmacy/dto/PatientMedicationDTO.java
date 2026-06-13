package com.act.hospitalmanagementsystem.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientMedicationDTO {
    private UUID id;
    private UUID patientId;
    private UUID drugId;
    private UUID prescriptionId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String dosage;
    private String frequency;
    private String route;
    private String instructions;
    private Boolean isActive;
    private Double adherence;
    private String notes;
    private LocalDateTime createdAt;
    private String createdBy;
}
