package com.act.hospitalmanagementsystem.medical.repository;

import com.act.hospitalmanagementsystem.medical.entity.MedicalRecord;
import com.act.hospitalmanagementsystem.medical.enums.RecordStatus;
import com.act.hospitalmanagementsystem.medical.enums.RecordType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, UUID> {

    Optional<MedicalRecord> findByIdAndDeletedFalse(UUID id);

    List<MedicalRecord> findByPatientIdAndDeletedFalseOrderByRecordDateDesc(UUID patientId);

    Page<MedicalRecord> findByPatientIdAndDeletedFalse(UUID patientId, Pageable pageable);

    Page<MedicalRecord> findByRecordTypeAndDeletedFalse(RecordType recordType, Pageable pageable);

    Page<MedicalRecord> findByPatientIdAndRecordTypeAndDeletedFalse(UUID patientId, RecordType recordType, Pageable pageable);

    Page<MedicalRecord> findByStatusAndDeletedFalse(RecordStatus status, Pageable pageable);

    @Query("SELECT m FROM MedicalRecord m WHERE m.deleted = false AND " +
           "(LOWER(m.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(m.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(m.clinicalNotes) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<MedicalRecord> searchMedicalRecords(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT m FROM MedicalRecord m WHERE m.deleted = false AND m.patientId = :patientId AND " +
           "(LOWER(m.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(m.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(m.clinicalNotes) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<MedicalRecord> searchPatientMedicalRecords(@Param("patientId") UUID patientId, @Param("searchTerm") String searchTerm, Pageable pageable);
}
