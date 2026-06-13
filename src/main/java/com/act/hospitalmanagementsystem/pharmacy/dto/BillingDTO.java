package com.act.hospitalmanagementsystem.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingDTO {
    private UUID id;
    private UUID dispensingId;
    private Double totalAmount;
    private Double discountAmount;
    private Double netAmount;
    private String paymentMethod;
    private String insuranceClaimId;
    private Boolean paid;
}
