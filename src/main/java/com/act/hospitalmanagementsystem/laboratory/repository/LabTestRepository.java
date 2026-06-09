package com.act.hospitalmanagementsystem.laboratory.repository;

import com.act.hospitalmanagementsystem.laboratory.entity.LabTest;
import com.act.hospitalmanagementsystem.laboratory.enums.LabTestCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LabTestRepository extends JpaRepository<LabTest, UUID> {
    Optional<LabTest> findByTestCode(String testCode);
    List<LabTest> findByTestCategory(LabTestCategory testCategory);
    List<LabTest> findByIsActiveTrue();
}
