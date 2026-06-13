package com.act.hospitalmanagementsystem.inventory.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.inventory.enums.RequestStatus;
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
@Table(name = "inventory_department_requests")
public class DepartmentRequest extends BaseEntity {

    @Column(name = "request_number", unique = true, nullable = false)
    private String requestNumber;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "requested_by", nullable = false)
    private UUID requestedBy;

    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @Column(name = "required_date")
    private LocalDate requiredDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;

    @Column(name = "priority", length = 20)
    private String priority = "MEDIUM"; // LOW, MEDIUM, HIGH, URGENT

    @Column(name = "purpose", columnDefinition = "TEXT")
    private String purpose;

    @Column(name = "items", columnDefinition = "JSON")
    private String items;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "approved_by")
    private UUID approvedBy;

    @Column(name = "approved_on")
    private java.time.LocalDateTime approvedOn;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
