package com.act.hospitalmanagementsystem.pharmacy.repository;

import com.act.hospitalmanagementsystem.pharmacy.entity.DrugInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DrugInteractionRepository extends JpaRepository<DrugInteraction, UUID> {

    List<DrugInteraction> findByDrug1Id(UUID drug1Id);

    List<DrugInteraction> findByDrug2Id(UUID drug2Id);

    @Query("SELECT di FROM DrugInteraction di WHERE di.deleted = false AND (di.drug1.id = :drugId OR di.drug2.id = :drugId)")
    List<DrugInteraction> findInteractionsForDrug(@Param("drugId") UUID drugId);
}
