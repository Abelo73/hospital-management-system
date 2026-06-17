package com.act.hospitalmanagementsystem.billing.mapper;

import com.act.hospitalmanagementsystem.billing.dto.*;
import com.act.hospitalmanagementsystem.billing.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BillingMapper {

    InvoiceDTO toDTO(Invoice invoice);

    @Mapping(target = "invoiceId", source = "invoice.id")
    @Mapping(target = "invoiceNumber", source = "invoice.invoiceNumber")
    PaymentDTO toDTO(Payment payment);

    InsuranceProviderDTO toDTO(InsuranceProvider provider);

    @Mapping(target = "invoiceId", source = "invoice.id")
    @Mapping(target = "invoiceNumber", source = "invoice.invoiceNumber")
    @Mapping(target = "providerId", source = "provider.id")
    @Mapping(target = "providerName", source = "provider.name")
    InsuranceClaimDTO toDTO(InsuranceClaim claim);

    InvoiceLineItemDTO toDTO(InvoiceLineItem item);
}
