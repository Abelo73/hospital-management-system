package com.act.hospitalmanagementsystem.medical.repository;

import com.act.hospitalmanagementsystem.medical.entity.Allergy;
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
public interface AllergyRepository extends JpaRepository<Allergy, UUID> {

    Optional<Allergy> findByIdAndDeletedFalse(UUID id);

    List<Allergy> findByPatientIdAndDeletedFalseOrderByOnsetDateDesc(UUID patientId);

    Page<Allergy> findByPatientIdAndDeletedFalse(UUID patientId, Pageable pageable);

    @Query("SELECT a FROM Allergy a WHERE a.deleted = false AND a.patientId = :patientId AND " +
           "LOWER(a.allergenName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Allergy> searchPatientAllergies(@Param("patientId") UUID patientId, @Param("searchTerm") String searchTerm, Pageable pageable);
}
