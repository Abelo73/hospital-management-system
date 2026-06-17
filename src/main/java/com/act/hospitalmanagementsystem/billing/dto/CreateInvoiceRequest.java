package com.act.hospitalmanagementsystem.billing.dto;

import com.act.hospitalmanagementsystem.billing.enums.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class CreateInvoiceRequest {
    @NotNull private UUID patientId;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    @NotEmpty private List<LineItemRequest> lineItems;
    private PaymentMethod paymentMethod;
    private String notes;

    @Data
    public static class LineItemRequest {
        private String serviceType;
        private UUID serviceId;
        @NotNull private String description;
        private Integer quantity = 1;
        @NotNull private java.math.BigDecimal unitPrice;
    }
}
