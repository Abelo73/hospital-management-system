package com.act.hospitalmanagementsystem.doctor.repository;

import com.act.hospitalmanagementsystem.doctor.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, UUID> {
    List<Diagnosis> findByConsultationId(UUID consultationId);
}
