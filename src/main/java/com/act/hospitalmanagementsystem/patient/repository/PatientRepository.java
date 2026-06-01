package com.act.hospitalmanagementsystem.patient.repository;

import com.act.hospitalmanagementsystem.patient.entity.Patient;
import com.act.hospitalmanagementsystem.patient.enums.PatientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    Optional<Patient> findByMedicalRecordNumberAndDeletedFalse(String medicalRecordNumber);

    Optional<Patient> findByIdAndDeletedFalse(UUID id);

    List<Patient> findByDeletedFalse();

    List<Patient> findByStatusAndDeletedFalse(PatientStatus status);

    @Query("SELECT p FROM Patient p WHERE p.deleted = false AND " +
           "(LOWER(p.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.medicalRecordNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.phoneNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Patient> searchPatients(@Param("searchTerm") String searchTerm);

    @Query("SELECT p FROM Patient p WHERE p.deleted = false AND p.dateOfBirth BETWEEN :startDate AND :endDate")
    List<Patient> findByDateOfBirthBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    boolean existsByMedicalRecordNumberAndDeletedFalse(String medicalRecordNumber);
}
