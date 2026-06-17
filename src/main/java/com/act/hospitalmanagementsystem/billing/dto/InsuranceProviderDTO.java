package com.act.hospitalmanagementsystem.billing.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class InsuranceProviderDTO {
    private UUID id;
    private String name;
    private String contactPerson;
    private String phone;
    private String email;
    private String address;
    private String claimSubmissionMethod;
    private BigDecimal standardCoPay;
    private BigDecimal standardDeductible;
    private Boolean isActive;
}
