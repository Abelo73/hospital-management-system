package com.act.hospitalmanagementsystem.pharmacy.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pharmacy_controlled_substance_logs")
public class ControlledSubstanceLog extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_id")
    private Drug drug;

    @Column(name = "transaction_type", nullable = false, length = 50)
    private String transactionType; // RECEIVED, DISPENSED, ADJUSTED, DESTROYED

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "batch_number")
    private String batchNumber;

    @Column(name = "prescription_number")
    private String prescriptionNumber;

    @Column(name = "performed_by", nullable = false)
    private UUID performedBy;

    @Column(name = "performed_at", nullable = false)
    private LocalDateTime performedAt;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "witnessed_by")
    private UUID witnessedBy;
}
