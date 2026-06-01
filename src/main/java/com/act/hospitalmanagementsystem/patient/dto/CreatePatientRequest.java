package com.act.hospitalmanagementsystem.patient.dto;

import com.act.hospitalmanagementsystem.patient.enums.BloodType;
import com.act.hospitalmanagementsystem.patient.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatientRequest {
    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is required")
    private Gender gender;

    private BloodType bloodType;

    @Pattern(regexp = "^\\+?[0-9]{7,20}$", message = "Invalid phone number format")
    private String phoneNumber;

    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @Size(max = 500, message = "Address must not exceed 500 characters")
    private String address;

    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @Size(max = 100, message = "State must not exceed 100 characters")
    private String state;

    @Size(max = 20, message = "Postal code must not exceed 20 characters")
    private String postalCode;

    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String country;

    @Size(max = 100, message = "Emergency contact name must not exceed 100 characters")
    private String emergencyContactName;

    @Pattern(regexp = "^\\+?[0-9]{7,20}$", message = "Invalid emergency contact phone format")
    private String emergencyContactPhone;

    @Size(max = 50, message = "Emergency contact relationship must not exceed 50 characters")
    private String emergencyContactRelationship;

    @Size(max = 1000, message = "Allergies must not exceed 1000 characters")
    private String allergies;

    @Size(max = 1000, message = "Chronic conditions must not exceed 1000 characters")
    private String chronicConditions;

    @Size(max = 1000, message = "Current medications must not exceed 1000 characters")
    private String currentMedications;

    @Size(max = 100, message = "Insurance provider must not exceed 100 characters")
    private String insuranceProvider;

    @Size(max = 50, message = "Insurance policy number must not exceed 50 characters")
    private String insurancePolicyNumber;

    @Size(max = 2000, message = "Notes must not exceed 2000 characters")
    private String notes;
}
