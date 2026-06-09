package com.act.hospitalmanagementsystem.doctor.repository;

import com.act.hospitalmanagementsystem.doctor.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {
    List<Prescription> findByConsultationId(UUID consultationId);
}
