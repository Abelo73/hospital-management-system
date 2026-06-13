package com.act.hospitalmanagementsystem.inventory.dto;

import com.act.hospitalmanagementsystem.inventory.enums.StockStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {
    private UUID id;
    private UUID itemId;
    private UUID locationId;
    private UUID batchId;
    private Integer quantity;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private Double unitCost;
    private Double totalCost;
    private LocalDate expiryDate;
    private LocalDate manufactureDate;
    private LocalDate lastReceivedDate;
    private LocalDate lastIssuedDate;
    private StockStatus status;
    private LocalDateTime createdAt;
    private String createdBy;
}
