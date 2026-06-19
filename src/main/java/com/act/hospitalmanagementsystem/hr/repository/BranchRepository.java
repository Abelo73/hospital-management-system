package com.act.hospitalmanagementsystem.hr.repository;

import com.act.hospitalmanagementsystem.hr.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BranchRepository extends JpaRepository<Branch, UUID> {

    Optional<Branch> findByCodeAndDeletedFalse(String code);

    List<Branch> findByDeletedFalseOrderByNameAsc();

    List<Branch> findByStatusAndDeletedFalse(String status);

    boolean existsByCodeAndDeletedFalse(String code);

    long countByParentBranchIdAndDeletedFalse(UUID parentBranchId);
}
