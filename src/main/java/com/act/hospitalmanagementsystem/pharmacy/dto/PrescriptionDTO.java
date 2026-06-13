package com.act.hospitalmanagementsystem.pharmacy.dto;

import com.act.hospitalmanagementsystem.pharmacy.enums.PrescriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDTO {
    private UUID id;
    private String prescriptionNumber;
    private UUID patientId;
    private UUID doctorId;
    private LocalDate prescriptionDate;
    private PrescriptionStatus status;
    private String priority;
    private String facility;
    private String department;
    private String notes;
    private UUID validatedBy;
    private LocalDateTime validatedAt;
    private UUID dispensedBy;
    private LocalDateTime dispensedAt;
    private LocalDateTime createdAt;
    private String createdBy;
}
