package com.act.hospitalmanagementsystem.pharmacy.dto;

import com.act.hospitalmanagementsystem.pharmacy.enums.DispensingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispensingDTO {
    private UUID id;
    private String dispensingNumber;
    private UUID prescriptionId;
    private UUID patientId;
    private LocalDate dispensingDate;
    private DispensingStatus status;
    private UUID dispensedBy;
    private UUID verifiedBy;
    private Double totalAmount;
    private Double discountAmount;
    private Double netAmount;
    private String paymentMethod;
    private String insuranceClaimId;
    private Boolean counselingProvided;
    private String counselingNotes;
    private LocalDateTime createdAt;
    private String createdBy;
}
