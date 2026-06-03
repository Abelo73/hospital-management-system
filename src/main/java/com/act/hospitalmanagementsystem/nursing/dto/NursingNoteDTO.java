package com.act.hospitalmanagementsystem.nursing.dto;

import com.act.hospitalmanagementsystem.nursing.enums.NoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NursingNoteDTO {
    private UUID id;
    private UUID patientId;
    private UUID admissionId;
    private NoteType noteType;
    private LocalDate noteDate;
    private LocalTime noteTime;
    private UUID nurseId;
    private String subject;
    private String narrative;
    private String careProvided;
    private String patientResponse;
    private Boolean followUpRequired;
    private String followUpAction;
    private Boolean isConfidential;
    private String attachments;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private UUID updatedBy;
    private LocalDateTime updatedAt;
}
