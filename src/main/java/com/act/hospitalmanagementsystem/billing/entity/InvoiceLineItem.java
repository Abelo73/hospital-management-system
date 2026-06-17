package com.act.hospitalmanagementsystem.billing.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity @Table(name = "billing_invoice_line_items")
public class InvoiceLineItem extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "invoice_id", nullable = false) private Invoice invoice;
    @Column(name = "service_type", length = 50) private String serviceType;
    @Column(name = "service_id") private UUID serviceId;
    @Column(name = "description", nullable = false, length = 500) private String description;
    @Column(name = "quantity", nullable = false) private Integer quantity = 1;
    @Column(name = "unit_price", nullable = false, precision = 12, scale = 2) private BigDecimal unitPrice;
    @Column(name = "line_total", nullable = false, precision = 12, scale = 2) private BigDecimal lineTotal;
}
