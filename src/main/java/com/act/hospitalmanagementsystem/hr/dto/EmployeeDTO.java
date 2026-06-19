package com.act.hospitalmanagementsystem.hr.dto;

import com.act.hospitalmanagementsystem.hr.enums.EmployeeStatus;
import com.act.hospitalmanagementsystem.hr.enums.EmployeeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private UUID id;
    private String employeeNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private EmployeeType employeeType;
    private String department;
    private String position;
    private LocalDate hireDate;
    private LocalDate terminationDate;
    private EmployeeStatus status;
    private Double salary;
    private String bankName;
    private String bankAccountNumber;
    private String taxId;
    private String socialSecurityNumber;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String emergencyContactRelationship;
    private String profilePictureUrl;
    private String notes;
    // Extended profile fields (V10)
    private String photoUrl;
    private String maritalStatus;
    private String nationality;
    private String religion;
    private String bloodGroup;
    private Boolean disabilityFlag;
    private String workLocation;
    private UUID branchId;
    private UUID positionId;
    private UUID supervisorEmployeeId;
    private String employmentCategory;
    private String passportNumber;
    private String nationalId;
    private String driverLicenceNumber;
    private String bankAccountHolder;
    private String bankBranch;
    private Double housingAllowance;
    private Double transportAllowance;
    private Double medicalAllowance;
    private Double mealAllowance;
    private UUID taxGroupId;
    private LocalDateTime createdAt;
    private String createdBy;
}
