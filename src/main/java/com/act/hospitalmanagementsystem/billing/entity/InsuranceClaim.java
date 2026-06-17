package com.act.hospitalmanagementsystem.billing.entity;

import com.act.hospitalmanagementsystem.billing.enums.ClaimStatus;
import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity @Table(name = "billing_insurance_claims")
public class InsuranceClaim extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "invoice_id") private Invoice invoice;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "provider_id") private InsuranceProvider provider;
    @Column(name = "patient_id", nullable = false) private UUID patientId;
    @Column(name = "claim_number", unique = true, length = 100) private String claimNumber;
    @Column(name = "submission_date") private LocalDate submissionDate;
    @Enumerated(EnumType.STRING) @Column(name = "status", nullable = false, length = 30) private ClaimStatus status = ClaimStatus.DRAFT;
    @Column(name = "approved_amount", precision = 12, scale = 2) private BigDecimal approvedAmount;
    @Column(name = "rejection_reason", length = 1000) private String rejectionReason;
    @Column(name = "notes", columnDefinition = "TEXT") private String notes;
}
