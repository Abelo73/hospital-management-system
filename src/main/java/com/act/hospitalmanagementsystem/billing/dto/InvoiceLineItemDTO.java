package com.act.hospitalmanagementsystem.billing.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class InvoiceLineItemDTO {
    private UUID id;
    private String serviceType;
    private UUID serviceId;
    private String description;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal lineTotal;
}
