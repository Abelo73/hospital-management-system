package com.act.hospitalmanagementsystem.inventory.repository;

import com.act.hospitalmanagementsystem.inventory.entity.StockIssue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockIssueJpaRepository extends JpaRepository<StockIssue, UUID> {

    Optional<StockIssue> findByIssueNumber(String issueNumber);

    Page<StockIssue> findByDepartment(String department, Pageable pageable);
}
