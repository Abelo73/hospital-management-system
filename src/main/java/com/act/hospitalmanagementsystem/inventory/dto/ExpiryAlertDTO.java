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
public class ExpiryAlertDTO {
    private UUID id;
    private UUID itemId;
    private UUID batchId;
    private UUID locationId;
    private LocalDate expiryDate;
    private Integer daysToExpiry;
    private Integer quantity;
    private String alertType;
    private LocalDate alertDate;
    private Boolean isAcknowledged;
    private UUID acknowledgedBy;
    private LocalDateTime acknowledgedOn;
    private String actionTaken;
    private LocalDateTime createdAt;
    private String createdBy;
}
