package com.act.hospitalmanagementsystem.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockIssueDTO {
    private UUID id;
    private String issueNumber;
    private UUID departmentRequestId;
    private String department;
    private UUID issuedBy;
    private LocalDate issueDate;
    private String items;
    private Integer totalQuantity;
    private Double totalAmount;
    private UUID receivedBy;
    private LocalDateTime receivedOn;
    private String notes;
    private LocalDateTime createdAt;
    private String createdBy;
}
