package com.act.hospitalmanagementsystem.pharmacy.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.pharmacy.enums.DispensingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pharmacy_dispensing")
public class Dispensing extends BaseEntity {

    @Column(name = "dispensing_number", unique = true, nullable = false)
    private String dispensingNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "dispensing_date", nullable = false)
    private LocalDate dispensingDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DispensingStatus status = DispensingStatus.PENDING;

    @Column(name = "dispensed_by")
    private UUID dispensedBy;

    @Column(name = "verified_by")
    private UUID verifiedBy;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "discount_amount")
    private Double discountAmount;

    @Column(name = "net_amount")
    private Double netAmount;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "insurance_claim_id")
    private String insuranceClaimId;

    @Column(name = "counseling_provided")
    private Boolean counselingProvided = false;

    @Column(name = "counseling_notes", columnDefinition = "TEXT")
    private String counselingNotes;

    @OneToMany(mappedBy = "dispensing", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DispensingItem> items;
}
