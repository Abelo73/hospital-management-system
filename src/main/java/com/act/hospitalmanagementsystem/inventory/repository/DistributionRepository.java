package com.act.hospitalmanagementsystem.inventory.repository;

import com.act.hospitalmanagementsystem.inventory.entity.DepartmentRequest;
import com.act.hospitalmanagementsystem.inventory.entity.StockIssue;
import com.act.hospitalmanagementsystem.inventory.entity.StockReturn;
import com.act.hospitalmanagementsystem.inventory.enums.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DistributionRepository {

    // Department Request operations
    DepartmentRequest saveDepartmentRequest(DepartmentRequest request);
    Optional<DepartmentRequest> findDepartmentRequestById(UUID id);
    Optional<DepartmentRequest> findDepartmentRequestByRequestNumber(String requestNumber);
    Page<DepartmentRequest> findAllDepartmentRequests(Pageable pageable);
    Page<DepartmentRequest> findDepartmentRequestsByStatus(RequestStatus status, Pageable pageable);
    Page<DepartmentRequest> findDepartmentRequestsByDepartment(String department, Pageable pageable);

    // Stock Issue operations
    StockIssue saveStockIssue(StockIssue issue);
    Optional<StockIssue> findStockIssueById(UUID id);
    Optional<StockIssue> findStockIssueByIssueNumber(String issueNumber);
    Page<StockIssue> findAllStockIssues(Pageable pageable);
    Page<StockIssue> findStockIssuesByDepartment(String department, Pageable pageable);

    // Stock Return operations
    StockReturn saveStockReturn(StockReturn stockReturn);
    Optional<StockReturn> findStockReturnById(UUID id);
    Optional<StockReturn> findStockReturnByReturnNumber(String returnNumber);
    Page<StockReturn> findAllStockReturns(Pageable pageable);
}
