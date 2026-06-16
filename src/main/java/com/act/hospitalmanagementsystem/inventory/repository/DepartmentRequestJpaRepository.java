package com.act.hospitalmanagementsystem.inventory.repository;

import com.act.hospitalmanagementsystem.inventory.entity.DepartmentRequest;
import com.act.hospitalmanagementsystem.inventory.enums.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepartmentRequestJpaRepository extends JpaRepository<DepartmentRequest, UUID> {

    Optional<DepartmentRequest> findByRequestNumber(String requestNumber);

    Page<DepartmentRequest> findByStatus(RequestStatus status, Pageable pageable);

    Page<DepartmentRequest> findByDepartment(String department, Pageable pageable);
}
