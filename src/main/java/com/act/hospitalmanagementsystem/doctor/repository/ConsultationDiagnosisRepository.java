package com.act.hospitalmanagementsystem.doctor.repository;

import com.act.hospitalmanagementsystem.doctor.entity.ConsultationDiagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultationDiagnosisRepository extends JpaRepository<ConsultationDiagnosis, UUID> {
    List<ConsultationDiagnosis> findByConsultationId(UUID consultationId);
}
