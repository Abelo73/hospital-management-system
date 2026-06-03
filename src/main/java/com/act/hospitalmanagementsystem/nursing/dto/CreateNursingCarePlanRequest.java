package com.act.hospitalmanagementsystem.nursing.dto;

import com.act.hospitalmanagementsystem.nursing.enums.CarePlanStatus;
import com.act.hospitalmanagementsystem.nursing.enums.CarePlanType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNursingCarePlanRequest {
    @NotNull(message = "Patient ID is required")
    private UUID patientId;

    private UUID admissionId;

    @NotBlank(message = "Plan name is required")
    @Size(max = 200, message = "Plan name must not exceed 200 characters")
    private String planName;

    @NotNull(message = "Plan type is required")
    private CarePlanType planType;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull(message = "Status is required")
    private CarePlanStatus status;

    private UUID primaryNurseId;

    @Size(max = 5000, message = "Assessment must not exceed 5000 characters")
    private String assessment;

    @Size(max = 500, message = "Nursing diagnosis must not exceed 500 characters")
    private String nursingDiagnosis;

    @Size(max = 5000, message = "Goals must not exceed 5000 characters")
    private String goals;

    @Size(max = 5000, message = "Interventions must not exceed 5000 characters")
    private String interventions;

    @Size(max = 5000, message = "Evaluation must not exceed 5000 characters")
    private String evaluation;
}
