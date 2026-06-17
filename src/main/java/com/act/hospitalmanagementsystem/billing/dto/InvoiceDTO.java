package com.act.hospitalmanagementsystem.billing.dto;

import com.act.hospitalmanagementsystem.billing.enums.InvoiceStatus;
import com.act.hospitalmanagementsystem.billing.enums.PaymentMethod;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class InvoiceDTO {
    private UUID id;
    private String invoiceNumber;
    private UUID patientId;
    private String patientName;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private InvoiceStatus status;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal discount;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private BigDecimal balanceAmount;
    private PaymentMethod paymentMethod;
    private String notes;
    private List<InvoiceLineItemDTO> lineItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
