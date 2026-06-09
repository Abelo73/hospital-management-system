package com.act.hospitalmanagementsystem.doctor.repository;

import com.act.hospitalmanagementsystem.doctor.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, UUID> {
    List<Consultation> findByPatientId(UUID patientId);
    List<Consultation> findByDoctorId(UUID doctorId);
}
