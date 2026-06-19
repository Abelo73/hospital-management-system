package com.act.hospitalmanagementsystem.hr.repository;

import com.act.hospitalmanagementsystem.hr.entity.Benefits;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BenefitsRepository extends JpaRepository<Benefits, UUID> {

    Page<Benefits> findByEmployeeId(UUID employeeId, Pageable pageable);

    Page<Benefits> findByBenefitType(String benefitType, Pageable pageable);

    Page<Benefits> findByStatus(String status, Pageable pageable);
}
