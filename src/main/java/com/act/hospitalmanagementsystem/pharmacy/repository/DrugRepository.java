package com.act.hospitalmanagementsystem.pharmacy.repository;

import com.act.hospitalmanagementsystem.pharmacy.entity.Drug;
import com.act.hospitalmanagementsystem.pharmacy.enums.DrugCategory;
import com.act.hospitalmanagementsystem.pharmacy.enums.DrugSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DrugRepository extends JpaRepository<Drug, UUID> {

    Optional<Drug> findByDrugCode(String drugCode);

    Page<Drug> findByDrugCategory(DrugCategory category, Pageable pageable);

    Page<Drug> findBySchedule(DrugSchedule schedule, Pageable pageable);

    Page<Drug> findByIsActiveTrue(Pageable pageable);

    @Query("SELECT d FROM Drug d WHERE d.deleted = false AND d.isActive = true AND (d.genericName LIKE %:query% OR d.brandName LIKE %:query%)")
    Page<Drug> searchDrugs(@Param("query") String query, Pageable pageable);

    @Query("SELECT d FROM Drug d WHERE d.deleted = false AND d.isControlledSubstance = true")
    Page<Drug> findControlledSubstances(Pageable pageable);
}
