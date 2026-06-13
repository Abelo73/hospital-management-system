package com.act.hospitalmanagementsystem.inventory.dto;

import com.act.hospitalmanagementsystem.inventory.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequestDTO {
    private UUID id;
    private String requestNumber;
    private String department;
    private UUID requestedBy;
    private LocalDate requestDate;
    private LocalDate requiredDate;
    private RequestStatus status;
    private String priority;
    private String purpose;
    private String items;
    private Integer totalQuantity;
    private Double totalAmount;
    private UUID approvedBy;
    private LocalDateTime approvedOn;
    private String notes;
    private LocalDateTime createdAt;
    private String createdBy;
}
