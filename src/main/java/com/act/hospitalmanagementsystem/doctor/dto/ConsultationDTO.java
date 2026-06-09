package com.act.hospitalmanagementsystem.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationDTO {
    private UUID id;
    private UUID patientId;
    private String patientName;
    private UUID doctorId;
    private String doctorName;
    private LocalDateTime consultationDate;
    private String chiefComplaint;
    private String historyOfPresentIllness;
    private String physicalExamination;
    private String plan;
    private List<DiagnosisDTO> diagnoses;
    private List<PrescriptionDTO> prescriptions;
    private String notes;
    private Boolean isFinalized;
    private LocalDateTime finalizedAt;
}
