package com.act.hospitalmanagementsystem.billing.dto;

import com.act.hospitalmanagementsystem.billing.enums.ClaimStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class InsuranceClaimDTO {
    private UUID id;
    private UUID invoiceId;
    private String invoiceNumber;
    private UUID providerId;
    private String providerName;
    private UUID patientId;
    private String claimNumber;
    private LocalDate submissionDate;
    private ClaimStatus status;
    private BigDecimal approvedAmount;
    private String rejectionReason;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
