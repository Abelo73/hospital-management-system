package com.act.hospitalmanagementsystem.hr.repository;

import com.act.hospitalmanagementsystem.hr.entity.Compliance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ComplianceRepository extends JpaRepository<Compliance, UUID> {

    Page<Compliance> findByEmployeeId(UUID employeeId, Pageable pageable);

    Page<Compliance> findByComplianceType(String complianceType, Pageable pageable);

    Page<Compliance> findByStatus(String status, Pageable pageable);
}
