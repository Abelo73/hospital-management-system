package com.act.hospitalmanagementsystem.medical.dto;

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
public class CreateVaccinationRequest {
    @NotNull(message = "Patient ID is required")
    private java.util.UUID patientId;

    @NotBlank(message = "Vaccine name is required")
    @Size(max = 200, message = "Vaccine name must not exceed 200 characters")
    private String vaccineName;

    @Size(max = 100, message = "Vaccine type must not exceed 100 characters")
    private String vaccineType;

    @NotNull(message = "Administration date is required")
    private LocalDate administrationDate;

    private Integer doseNumber;

    @Size(max = 50, message = "Lot number must not exceed 50 characters")
    private String lotNumber;

    private java.util.UUID administeringProviderId;

    private LocalDate nextDueDate;

    @Size(max = 2000, message = "Notes must not exceed 2000 characters")
    private String notes;
}
