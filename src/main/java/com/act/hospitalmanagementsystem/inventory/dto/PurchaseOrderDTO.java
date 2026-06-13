package com.act.hospitalmanagementsystem.inventory.dto;

import com.act.hospitalmanagementsystem.inventory.enums.ProcurementStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDTO {
    private UUID id;
    private String orderNumber;
    private UUID supplierId;
    private LocalDate orderDate;
    private LocalDate expectedDeliveryDate;
    private LocalDate actualDeliveryDate;
    private ProcurementStatus status;
    private Double totalAmount;
    private Double taxAmount;
    private Double discountAmount;
    private Double netAmount;
    private String currency;
    private String paymentMethod;
    private String deliveryMethod;
    private String shippingAddress;
    private String billingAddress;
    private String notes;
    private UUID approvedBy;
    private LocalDateTime approvedOn;
    private LocalDateTime createdAt;
    private String createdBy;
}
