package com.act.hospitalmanagementsystem.medical.dto;

import com.act.hospitalmanagementsystem.medical.enums.MedicationRoute;
import com.act.hospitalmanagementsystem.medical.enums.MedicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicationDTO {
    private UUID id;
    private UUID patientId;
    private String medicationName;
    private String genericName;
    private String dosage;
    private String frequency;
    private MedicationRoute route;
    private LocalDate startDate;
    private LocalDate endDate;
    private UUID prescribingPhysicianId;
    private MedicationStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
