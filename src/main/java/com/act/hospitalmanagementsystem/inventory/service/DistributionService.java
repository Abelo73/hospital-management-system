package com.act.hospitalmanagementsystem.inventory.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.inventory.dto.DepartmentRequestDTO;
import com.act.hospitalmanagementsystem.inventory.dto.StockIssueDTO;
import com.act.hospitalmanagementsystem.inventory.entity.DepartmentRequest;
import com.act.hospitalmanagementsystem.inventory.entity.StockIssue;
import com.act.hospitalmanagementsystem.inventory.enums.RequestStatus;
import com.act.hospitalmanagementsystem.inventory.mapper.DistributionMapper;
import com.act.hospitalmanagementsystem.inventory.repository.DistributionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DistributionService {

    private final DistributionRepository distributionRepository;
    private final DistributionMapper distributionMapper;

    @Transactional
    public BaseResponseDTO<DepartmentRequestDTO> createDepartmentRequest(String department, LocalDate requiredDate,
            String priority, String purpose, List<Map<String, Object>> items, String requestedBy) {
        try {
            DepartmentRequest request = new DepartmentRequest();
            request.setRequestNumber(generateRequestNumber());
            request.setDepartment(department);
            request.setRequestedBy(UUID.fromString(requestedBy));
            request.setRequestDate(LocalDate.now());
            request.setRequiredDate(requiredDate);
            request.setStatus(RequestStatus.PENDING);
            request.setPriority(priority);
            request.setPurpose(purpose);
            request.setItems(items.toString());
            request.setTotalQuantity(calculateTotalQuantity(items));
            request.setCreatedBy(requestedBy);

            DepartmentRequest saved = distributionRepository.saveDepartmentRequest(request);
            return BaseResponseDTO.success(distributionMapper.toDTO(saved), "Department request created successfully");
        } catch (Exception e) {
            log.error("Error creating department request", e);
            return BaseResponseDTO.error("Failed to create department request: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<DepartmentRequestDTO>> getPendingRequests(String department, Pageable pageable) {
        try {
            Page<DepartmentRequest> requests;
            if (department != null && !department.equals("ALL")) {
                requests = distributionRepository.findDepartmentRequestsByDepartment(department, pageable);
            } else {
                requests = distributionRepository.findDepartmentRequestsByStatus(RequestStatus.PENDING, pageable);
            }
            return BaseResponseDTO.success(distributionMapper.toDTOList(requests.getContent()), "Pending requests retrieved");
        } catch (Exception e) {
            log.error("Error getting pending requests", e);
            return BaseResponseDTO.error("Failed to get pending requests: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> approveRequest(UUID requestId, Boolean approved, String notes, String approvedBy) {
        try {
            DepartmentRequest request = distributionRepository.findDepartmentRequestById(requestId)
                    .orElseThrow(() -> new RuntimeException("Request not found"));

            if (approved) {
                request.setStatus(RequestStatus.APPROVED);
                request.setApprovedBy(UUID.fromString(approvedBy));
                request.setApprovedOn(java.time.LocalDateTime.now());
            } else {
                request.setStatus(RequestStatus.REJECTED);
            }
            request.setNotes(notes);
            request.setUpdatedBy(approvedBy);

            distributionRepository.saveDepartmentRequest(request);
            return BaseResponseDTO.success(null, approved ? "Request approved" : "Request rejected");
        } catch (Exception e) {
            log.error("Error approving request", e);
            return BaseResponseDTO.error("Failed to approve request: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<StockIssueDTO> issueStock(UUID requestId, List<Map<String, Object>> items, UUID receivedBy) {
        try {
            DepartmentRequest request = distributionRepository.findDepartmentRequestById(requestId)
                    .orElseThrow(() -> new RuntimeException("Request not found"));

            if (request.getStatus() != RequestStatus.APPROVED) {
                return BaseResponseDTO.error("Request must be approved before issuing stock");
            }

            StockIssue issue = new StockIssue();
            issue.setIssueNumber(generateIssueNumber());
            issue.setDepartmentRequest(request);
            issue.setDepartment(request.getDepartment());
            issue.setIssuedBy(UUID.fromString(receivedBy.toString()));
            issue.setIssueDate(LocalDate.now());
            issue.setItems(items.toString());
            issue.setTotalQuantity(calculateTotalQuantity(items));
            issue.setCreatedBy(receivedBy.toString());

            // TODO: Update stock levels based on issued items

            StockIssue saved = distributionRepository.saveStockIssue(issue);

            // Update request status
            request.setStatus(RequestStatus.ISSUED);
            distributionRepository.saveDepartmentRequest(request);

            return BaseResponseDTO.success(distributionMapper.toDTO(saved), "Stock issued successfully");
        } catch (Exception e) {
            log.error("Error issuing stock", e);
            return BaseResponseDTO.error("Failed to issue stock: " + e.getMessage());
        }
    }

    private String generateRequestNumber() {
        return "DR-" + System.currentTimeMillis();
    }

    private String generateIssueNumber() {
        return "SI-" + System.currentTimeMillis();
    }

    private Integer calculateTotalQuantity(List<Map<String, Object>> items) {
        return items.stream()
                .mapToInt(item -> (Integer) item.get("quantity"))
                .sum();
    }
}
