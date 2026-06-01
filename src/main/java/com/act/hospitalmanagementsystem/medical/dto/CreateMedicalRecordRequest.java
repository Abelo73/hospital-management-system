package com.act.hospitalmanagementsystem.medical.dto;

import com.act.hospitalmanagementsystem.medical.enums.RecordStatus;
import com.act.hospitalmanagementsystem.medical.enums.RecordType;
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
public class CreateMedicalRecordRequest {
    @NotNull(message = "Patient ID is required")
    private java.util.UUID patientId;

    @NotNull(message = "Record type is required")
    private RecordType recordType;

    @NotNull(message = "Record date is required")
    private LocalDate recordDate;

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @Size(max = 5000, message = "Clinical notes must not exceed 5000 characters")
    private String clinicalNotes;

    @NotNull(message = "Status is required")
    private RecordStatus status;
}
