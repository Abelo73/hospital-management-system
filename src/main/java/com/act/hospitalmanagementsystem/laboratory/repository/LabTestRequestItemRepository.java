package com.act.hospitalmanagementsystem.laboratory.repository;

import com.act.hospitalmanagementsystem.laboratory.entity.LabTestRequestItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LabTestRequestItemRepository extends JpaRepository<LabTestRequestItem, UUID> {
    List<LabTestRequestItem> findByRequestId(UUID requestId);
}
