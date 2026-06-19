package com.act.hospitalmanagementsystem.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchDTO {
    private UUID id;
    private String name;
    private String code;
    private String address;
    private String city;
    private String country;
    private String phone;
    private String email;
    private String branchType;
    private UUID parentBranchId;
    private String status;
    private LocalDateTime createdAt;
    private String createdBy;
}
