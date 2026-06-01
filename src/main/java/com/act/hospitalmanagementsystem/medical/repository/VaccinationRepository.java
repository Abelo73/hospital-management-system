package com.act.hospitalmanagementsystem.medical.repository;

import com.act.hospitalmanagementsystem.medical.entity.Vaccination;
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
public interface VaccinationRepository extends JpaRepository<Vaccination, UUID> {

    Optional<Vaccination> findByIdAndDeletedFalse(UUID id);

    List<Vaccination> findByPatientIdAndDeletedFalseOrderByAdministrationDateDesc(UUID patientId);

    Page<Vaccination> findByPatientIdAndDeletedFalse(UUID patientId, Pageable pageable);

    @Query("SELECT v FROM Vaccination v WHERE v.deleted = false AND v.patientId = :patientId AND " +
           "LOWER(v.vaccineName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Vaccination> searchPatientVaccinations(@Param("patientId") UUID patientId, @Param("searchTerm") String searchTerm, Pageable pageable);
}
