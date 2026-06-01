package com.act.hospitalmanagementsystem.medical.dto;

import com.act.hospitalmanagementsystem.medical.enums.LabResultStatus;
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
public class CreateLabResultRequest {
    @NotNull(message = "Patient ID is required")
    private java.util.UUID patientId;

    @Size(max = 100, message = "Test type must not exceed 100 characters")
    private String testType;

    @NotBlank(message = "Test name is required")
    @Size(max = 200, message = "Test name must not exceed 200 characters")
    private String testName;

    @NotNull(message = "Test date is required")
    private LocalDate testDate;

    @Size(max = 500, message = "Result value must not exceed 500 characters")
    private String resultValue;

    @Size(max = 50, message = "Unit must not exceed 50 characters")
    private String unit;

    @Size(max = 500, message = "Reference range must not exceed 500 characters")
    private String referenceRange;

    @NotNull(message = "Status is required")
    private LabResultStatus status;

    @Size(max = 200, message = "Performed by must not exceed 200 characters")
    private String performedBy;

    @Size(max = 2000, message = "Notes must not exceed 2000 characters")
    private String notes;
}
