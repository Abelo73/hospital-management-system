package com.act.hospitalmanagementsystem.medical.dto;

import com.act.hospitalmanagementsystem.medical.enums.RecordStatus;
import com.act.hospitalmanagementsystem.medical.enums.RecordType;
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
public class MedicalRecordDTO {
    private UUID id;
    private UUID patientId;
    private RecordType recordType;
    private LocalDate recordDate;
    private String title;
    private String description;
    private String clinicalNotes;
    private RecordStatus status;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private UUID updatedBy;
    private LocalDateTime updatedAt;
}
