package com.act.hospitalmanagementsystem.pharmacy.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pharmacy_dispensing_items")
public class DispensingItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispensing_id")
    private Dispensing dispensing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_item_id")
    private PrescriptionItem prescriptionItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_id")
    private Drug drug;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "batch_id")
    private UUID batchId;

    @Column(name = "unit_cost")
    private Double unitCost;

    @Column(name = "total_cost")
    private Double totalCost;
}
