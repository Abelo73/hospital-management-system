package com.act.hospitalmanagementsystem.inventory.mapper;

import com.act.hospitalmanagementsystem.inventory.dto.DepartmentRequestDTO;
import com.act.hospitalmanagementsystem.inventory.dto.StockIssueDTO;
import com.act.hospitalmanagementsystem.inventory.entity.DepartmentRequest;
import com.act.hospitalmanagementsystem.inventory.entity.StockIssue;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DistributionMapper {

    public DepartmentRequestDTO toDTO(DepartmentRequest request) {
        if (request == null) {
            return null;
        }

        DepartmentRequestDTO dto = new DepartmentRequestDTO();
        dto.setId(request.getId());
        dto.setRequestNumber(request.getRequestNumber());
        dto.setDepartment(request.getDepartment());
        dto.setRequestedBy(request.getRequestedBy());
        dto.setRequestDate(request.getRequestDate());
        dto.setRequiredDate(request.getRequiredDate());
        dto.setStatus(request.getStatus());
        dto.setPriority(request.getPriority());
        dto.setPurpose(request.getPurpose());
        dto.setItems(request.getItems());
        dto.setTotalQuantity(request.getTotalQuantity());
        dto.setTotalAmount(request.getTotalAmount());
        dto.setApprovedBy(request.getApprovedBy());
        dto.setApprovedOn(request.getApprovedOn());
        dto.setNotes(request.getNotes());
        dto.setCreatedAt(request.getCreatedAt());
        dto.setCreatedBy(request.getCreatedBy());
        return dto;
    }

    public StockIssueDTO toDTO(StockIssue issue) {
        if (issue == null) {
            return null;
        }

        StockIssueDTO dto = new StockIssueDTO();
        dto.setId(issue.getId());
        dto.setIssueNumber(issue.getIssueNumber());
        dto.setDepartmentRequestId(issue.getDepartmentRequest() != null ? issue.getDepartmentRequest().getId() : null);
        dto.setDepartment(issue.getDepartment());
        dto.setIssuedBy(issue.getIssuedBy());
        dto.setIssueDate(issue.getIssueDate());
        dto.setItems(issue.getItems());
        dto.setTotalQuantity(issue.getTotalQuantity());
        dto.setTotalAmount(issue.getTotalAmount());
        dto.setReceivedBy(issue.getReceivedBy());
        dto.setReceivedOn(issue.getReceivedOn());
        dto.setNotes(issue.getNotes());
        dto.setCreatedAt(issue.getCreatedAt());
        dto.setCreatedBy(issue.getCreatedBy());
        return dto;
    }

    public List<DepartmentRequestDTO> toDTOList(List<DepartmentRequest> requests) {
        return requests.stream().map(this::toDTO).toList();
    }

    public List<StockIssueDTO> toDTOList(List<StockIssue> issues) {
        return issues.stream().map(this::toDTO).toList();
    }
}
