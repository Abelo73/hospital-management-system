package com.act.hospitalmanagementsystem.laboratory.repository;

import com.act.hospitalmanagementsystem.laboratory.entity.LabTestRequest;
import com.act.hospitalmanagementsystem.laboratory.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LabTestRequestRepository extends JpaRepository<LabTestRequest, UUID> {
    Optional<LabTestRequest> findByRequestNumber(String requestNumber);
    List<LabTestRequest> findByPatientId(UUID patientId);
    List<LabTestRequest> findByStatus(RequestStatus status);
}
