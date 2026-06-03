package com.act.hospitalmanagementsystem.nursing.dto;

import com.act.hospitalmanagementsystem.nursing.enums.NoteType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNursingNoteRequest {
    private UUID admissionId;

    @NotNull(message = "Note type is required")
    private NoteType noteType;

    @NotNull(message = "Note date is required")
    private LocalDate noteDate;

    @NotNull(message = "Note time is required")
    private LocalTime noteTime;

    private UUID nurseId;

    @Size(max = 200, message = "Subject must not exceed 200 characters")
    private String subject;

    @Size(max = 5000, message = "Narrative must not exceed 5000 characters")
    private String narrative;

    @Size(max = 5000, message = "Care provided must not exceed 5000 characters")
    private String careProvided;

    @Size(max = 500, message = "Patient response must not exceed 500 characters")
    private String patientResponse;

    private Boolean followUpRequired;

    @Size(max = 500, message = "Follow up action must not exceed 500 characters")
    private String followUpAction;

    private Boolean isConfidential;

    @Size(max = 5000, message = "Attachments must not exceed 5000 characters")
    private String attachments;
}
