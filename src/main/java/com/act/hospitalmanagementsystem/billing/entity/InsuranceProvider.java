package com.act.hospitalmanagementsystem.billing.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity @Table(name = "billing_insurance_providers")
public class InsuranceProvider extends BaseEntity {
    @Column(name = "name", nullable = false, length = 200) private String name;
    @Column(name = "contact_person", length = 100) private String contactPerson;
    @Column(name = "phone", length = 20) private String phone;
    @Column(name = "email", length = 100) private String email;
    @Column(name = "address", columnDefinition = "TEXT") private String address;
    @Column(name = "claim_submission_method", length = 20) private String claimSubmissionMethod;
    @Column(name = "standard_co_pay", precision = 10, scale = 2) private BigDecimal standardCoPay;
    @Column(name = "standard_deductible", precision = 10, scale = 2) private BigDecimal standardDeductible;
    @Column(name = "is_active", nullable = false) private Boolean isActive = true;
}
