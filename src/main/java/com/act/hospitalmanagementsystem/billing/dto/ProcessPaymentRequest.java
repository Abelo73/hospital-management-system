package com.act.hospitalmanagementsystem.billing.dto;

import com.act.hospitalmanagementsystem.billing.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProcessPaymentRequest {
    @NotNull private UUID invoiceId;
    @NotNull private PaymentMethod paymentMethod;
    @NotNull @Positive private BigDecimal amount;
    private String referenceNumber;
    private String notes;
}
