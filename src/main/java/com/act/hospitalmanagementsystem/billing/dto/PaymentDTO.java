package com.act.hospitalmanagementsystem.billing.dto;

import com.act.hospitalmanagementsystem.billing.enums.PaymentMethod;
import com.act.hospitalmanagementsystem.billing.enums.PaymentStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PaymentDTO {
    private UUID id;
    private UUID invoiceId;
    private String invoiceNumber;
    private UUID patientId;
    private LocalDate paymentDate;
    private PaymentMethod paymentMethod;
    private BigDecimal amount;
    private String referenceNumber;
    private PaymentStatus status;
    private String notes;
    private LocalDateTime createdAt;
}
