package com.act.hospitalmanagementsystem.hr.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.hr.enums.EmployeeStatus;
import com.act.hospitalmanagementsystem.hr.enums.EmployeeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "hr_employees")
public class Employee extends BaseEntity {

    @Column(name = "employee_number", unique = true, nullable = false)
    private String employeeNumber;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "employee_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EmployeeType employeeType;

    @Column(name = "department")
    private String department;

    @Column(name = "position")
    private String position;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Column(name = "termination_date")
    private LocalDate terminationDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status = EmployeeStatus.ACTIVE;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "social_security_number")
    private String socialSecurityNumber;

    @Column(name = "emergency_contact_name")
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone")
    private String emergencyContactPhone;

    @Column(name = "emergency_contact_relationship")
    private String emergencyContactRelationship;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
