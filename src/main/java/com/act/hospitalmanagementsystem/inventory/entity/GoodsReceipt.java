package com.act.hospitalmanagementsystem.inventory.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
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
@Table(name = "inventory_goods_receipts")
public class GoodsReceipt extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @Column(name = "items", columnDefinition = "JSON")
    private String items;

    @Column(name = "received_by", nullable = false)
    private UUID receivedBy;

    @Column(name = "received_on", nullable = false)
    private LocalDate receivedOn;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
