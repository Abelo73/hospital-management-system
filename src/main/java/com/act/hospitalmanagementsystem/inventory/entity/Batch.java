package com.act.hospitalmanagementsystem.inventory.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "inventory_batches")
public class Batch extends BaseEntity {

    @Column(name = "batch_number", nullable = false)
    private String batchNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "manufacture_date")
    private LocalDate manufactureDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "received_date")
    private LocalDate receivedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @Column(name = "cost_per_unit")
    private Double costPerUnit;

    @Column(name = "total_cost")
    private Double totalCost;

    @Column(name = "storage_location")
    private String storageLocation;

    @Column(name = "quality_check_status", length = 20)
    private String qualityCheckStatus;

    @Column(name = "quality_check_date")
    private LocalDate qualityCheckDate;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
