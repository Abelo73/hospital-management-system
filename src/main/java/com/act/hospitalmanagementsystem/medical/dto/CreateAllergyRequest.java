package com.act.hospitalmanagementsystem.medical.dto;

import com.act.hospitalmanagementsystem.medical.enums.AllergenType;
import com.act.hospitalmanagementsystem.medical.enums.Severity;
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
public class CreateAllergyRequest {
    @NotNull(message = "Patient ID is required")
    private java.util.UUID patientId;

    @NotNull(message = "Allergen type is required")
    private AllergenType allergenType;

    @NotBlank(message = "Allergen name is required")
    @Size(max = 200, message = "Allergen name must not exceed 200 characters")
    private String allergenName;

    @NotNull(message = "Severity is required")
    private Severity severity;

    @Size(max = 500, message = "Reaction must not exceed 500 characters")
    private String reaction;

    private LocalDate onsetDate;

    @Size(max = 100, message = "Reported by must not exceed 100 characters")
    private String reportedBy;
}
