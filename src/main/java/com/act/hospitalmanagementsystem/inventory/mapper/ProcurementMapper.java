package com.act.hospitalmanagementsystem.inventory.mapper;

import com.act.hospitalmanagementsystem.inventory.dto.PurchaseOrderDTO;
import com.act.hospitalmanagementsystem.inventory.entity.PurchaseOrder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProcurementMapper {

    public PurchaseOrderDTO toDTO(PurchaseOrder order) {
        if (order == null) {
            return null;
        }

        PurchaseOrderDTO dto = new PurchaseOrderDTO();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setSupplierId(order.getSupplier() != null ? order.getSupplier().getId() : null);
        dto.setOrderDate(order.getOrderDate());
        dto.setExpectedDeliveryDate(order.getExpectedDeliveryDate());
        dto.setActualDeliveryDate(order.getActualDeliveryDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setTaxAmount(order.getTaxAmount());
        dto.setDiscountAmount(order.getDiscountAmount());
        dto.setNetAmount(order.getNetAmount());
        dto.setCurrency(order.getCurrency());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setDeliveryMethod(order.getDeliveryMethod());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setBillingAddress(order.getBillingAddress());
        dto.setNotes(order.getNotes());
        dto.setApprovedBy(order.getApprovedBy());
        dto.setApprovedOn(order.getApprovedOn());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setCreatedBy(order.getCreatedBy());
        return dto;
    }

    public List<PurchaseOrderDTO> toDTOList(List<PurchaseOrder> orders) {
        return orders.stream().map(this::toDTO).toList();
    }
}
