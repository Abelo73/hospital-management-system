package com.act.hospitalmanagementsystem.patient.dto;

import com.act.hospitalmanagementsystem.patient.enums.BloodType;
import com.act.hospitalmanagementsystem.patient.enums.Gender;
import com.act.hospitalmanagementsystem.patient.enums.PatientStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private String id;
    private String medicalRecordNumber;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private BloodType bloodType;
    private String phoneNumber;
    private String email;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String emergencyContactRelationship;
    private String allergies;
    private String chronicConditions;
    private String currentMedications;
    private String insuranceProvider;
    private String insurancePolicyNumber;
    private LocalDateTime registrationDate;
    private PatientStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
