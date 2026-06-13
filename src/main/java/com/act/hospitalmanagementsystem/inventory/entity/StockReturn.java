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
@Table(name = "inventory_stock_returns")
public class StockReturn extends BaseEntity {

    @Column(name = "return_number", unique = true, nullable = false)
    private String returnNumber;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "returned_by", nullable = false)
    private UUID returnedBy;

    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;

    @Column(name = "items", columnDefinition = "JSON")
    private String items;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "received_by")
    private UUID receivedBy;

    @Column(name = "received_on")
    private java.time.LocalDateTime receivedOn;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
