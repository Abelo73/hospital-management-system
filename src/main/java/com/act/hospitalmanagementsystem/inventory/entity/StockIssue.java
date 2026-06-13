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
@Table(name = "inventory_stock_issues")
public class StockIssue extends BaseEntity {

    @Column(name = "issue_number", unique = true, nullable = false)
    private String issueNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_request_id")
    private DepartmentRequest departmentRequest;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "issued_by", nullable = false)
    private UUID issuedBy;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "items", columnDefinition = "JSON")
    private String items;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "received_by")
    private UUID receivedBy;

    @Column(name = "received_on")
    private java.time.LocalDateTime receivedOn;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
