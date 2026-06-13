package com.act.hospitalmanagementsystem.pharmacy.entity;

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
@Table(name = "pharmacy_stock")
public class PharmacyStock extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_id")
    private Drug drug;

    @Column(name = "batch_number")
    private String batchNumber;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "manufacture_date")
    private LocalDate manufactureDate;

    @Column(name = "location")
    private String location;

    @Column(name = "unit_cost")
    private Double unitCost;

    @Column(name = "selling_price")
    private Double sellingPrice;

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "received_date")
    private LocalDate receivedDate;

    @Column(name = "status", length = 20)
    private String status; // AVAILABLE, RESERVED, EXPIRED, RECALLED
}
