package com.act.hospitalmanagementsystem.medical.dto;

import com.act.hospitalmanagementsystem.medical.enums.MedicationRoute;
import com.act.hospitalmanagementsystem.medical.enums.MedicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMedicationRequest {
    @NotNull(message = "Patient ID is required")
    private java.util.UUID patientId;

    @NotBlank(message = "Medication name is required")
    @Size(max = 200, message = "Medication name must not exceed 200 characters")
    private String medicationName;

    @Size(max = 200, message = "Generic name must not exceed 200 characters")
    private String genericName;

    @Size(max = 100, message = "Dosage must not exceed 100 characters")
    private String dosage;

    @Size(max = 100, message = "Frequency must not exceed 100 characters")
    private String frequency;

    private MedicationRoute route;

    private LocalDate startDate;

    private LocalDate endDate;

    private java.util.UUID prescribingPhysicianId;

    @NotNull(message = "Status is required")
    private MedicationStatus status;

    @Size(max = 2000, message = "Notes must not exceed 2000 characters")
    private String notes;
}
