package com.act.hospitalmanagementsystem.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDTO {
    private UUID id;
    private String supplierCode;
    private String supplierName;
    private String contactPerson;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String taxId;
    private String paymentTerms;
    private String deliveryTerms;
    private Double creditLimit;
    private Double rating;
    private Boolean isActive;
    private String notes;
    private LocalDateTime createdAt;
    private String createdBy;
}
