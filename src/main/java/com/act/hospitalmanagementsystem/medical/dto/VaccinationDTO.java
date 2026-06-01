package com.act.hospitalmanagementsystem.medical.dto;

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
public class VaccinationDTO {
    private UUID id;
    private UUID patientId;
    private String vaccineName;
    private String vaccineType;
    private LocalDate administrationDate;
    private Integer doseNumber;
    private String lotNumber;
    private UUID administeringProviderId;
    private LocalDate nextDueDate;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
