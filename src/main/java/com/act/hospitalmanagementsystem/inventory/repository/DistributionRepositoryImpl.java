package com.act.hospitalmanagementsystem.inventory.repository;

import com.act.hospitalmanagementsystem.inventory.entity.DepartmentRequest;
import com.act.hospitalmanagementsystem.inventory.entity.StockIssue;
import com.act.hospitalmanagementsystem.inventory.entity.StockReturn;
import com.act.hospitalmanagementsystem.inventory.enums.RequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DistributionRepositoryImpl implements DistributionRepository {

    private final DepartmentRequestJpaRepository departmentRequestRepo;
    private final StockIssueJpaRepository stockIssueRepo;
    private final StockReturnJpaRepository stockReturnRepo;

    // Department Request operations
    @Override
    public DepartmentRequest saveDepartmentRequest(DepartmentRequest request) {
        return departmentRequestRepo.save(request);
    }

    @Override
    public Optional<DepartmentRequest> findDepartmentRequestById(UUID id) {
        return departmentRequestRepo.findById(id);
    }

    @Override
    public Optional<DepartmentRequest> findDepartmentRequestByRequestNumber(String requestNumber) {
        return departmentRequestRepo.findByRequestNumber(requestNumber);
    }

    @Override
    public Page<DepartmentRequest> findAllDepartmentRequests(Pageable pageable) {
        return departmentRequestRepo.findAll(pageable);
    }

    @Override
    public Page<DepartmentRequest> findDepartmentRequestsByStatus(RequestStatus status, Pageable pageable) {
        return departmentRequestRepo.findByStatus(status, pageable);
    }

    @Override
    public Page<DepartmentRequest> findDepartmentRequestsByDepartment(String department, Pageable pageable) {
        return departmentRequestRepo.findByDepartment(department, pageable);
    }

    // Stock Issue operations
    @Override
    public StockIssue saveStockIssue(StockIssue issue) {
        return stockIssueRepo.save(issue);
    }

    @Override
    public Optional<StockIssue> findStockIssueById(UUID id) {
        return stockIssueRepo.findById(id);
    }

    @Override
    public Optional<StockIssue> findStockIssueByIssueNumber(String issueNumber) {
        return stockIssueRepo.findByIssueNumber(issueNumber);
    }

    @Override
    public Page<StockIssue> findAllStockIssues(Pageable pageable) {
        return stockIssueRepo.findAll(pageable);
    }

    @Override
    public Page<StockIssue> findStockIssuesByDepartment(String department, Pageable pageable) {
        return stockIssueRepo.findByDepartment(department, pageable);
    }

    // Stock Return operations
    @Override
    public StockReturn saveStockReturn(StockReturn stockReturn) {
        return stockReturnRepo.save(stockReturn);
    }

    @Override
    public Optional<StockReturn> findStockReturnById(UUID id) {
        return stockReturnRepo.findById(id);
    }

    @Override
    public Optional<StockReturn> findStockReturnByReturnNumber(String returnNumber) {
        return stockReturnRepo.findByReturnNumber(returnNumber);
    }

    @Override
    public Page<StockReturn> findAllStockReturns(Pageable pageable) {
        return stockReturnRepo.findAll(pageable);
    }
}
