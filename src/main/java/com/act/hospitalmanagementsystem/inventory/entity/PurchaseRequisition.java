package com.act.hospitalmanagementsystem.inventory.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.inventory.enums.ProcurementStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "inventory_purchase_requisitions")
public class PurchaseRequisition extends BaseEntity {

    @Column(name = "requisition_number", unique = true, nullable = false)
    private String requisitionNumber;

    @Column(name = "items", columnDefinition = "JSON")
    private String items;

    @Column(name = "priority", length = 20)
    private String priority = "MEDIUM";

    @Column(name = "required_date")
    private LocalDate requiredDate;

    @Column(name = "purpose", columnDefinition = "TEXT")
    private String purpose;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcurementStatus status = ProcurementStatus.DRAFT;

    @Column(name = "requested_by")
    private UUID requestedBy;

    @Column(name = "requested_on")
    private java.time.LocalDateTime requestedOn;

    @Column(name = "approved_by")
    private UUID approvedBy;

    @Column(name = "approved_on")
    private java.time.LocalDateTime approvedOn;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
