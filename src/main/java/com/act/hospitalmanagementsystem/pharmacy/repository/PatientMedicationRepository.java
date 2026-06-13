package com.act.hospitalmanagementsystem.pharmacy.repository;

import com.act.hospitalmanagementsystem.pharmacy.entity.PatientMedication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PatientMedicationRepository extends JpaRepository<PatientMedication, UUID> {

    List<PatientMedication> findByPatientId(UUID patientId);

    List<PatientMedication> findByPatientIdAndIsActiveTrue(UUID patientId);

    List<PatientMedication> findByDrugId(UUID drugId);
}
