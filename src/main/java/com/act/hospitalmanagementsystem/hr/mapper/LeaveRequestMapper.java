package com.act.hospitalmanagementsystem.hr.mapper;

import com.act.hospitalmanagementsystem.hr.dto.LeaveRequestDTO;
import com.act.hospitalmanagementsystem.hr.entity.LeaveRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LeaveRequestMapper {

    public LeaveRequestDTO toDTO(LeaveRequest leaveRequest) {
        if (leaveRequest == null) {
            return null;
        }

        LeaveRequestDTO dto = new LeaveRequestDTO();
        dto.setId(leaveRequest.getId());
        dto.setEmployeeId(leaveRequest.getEmployeeId());
        dto.setLeaveType(leaveRequest.getLeaveType());
        dto.setStartDate(leaveRequest.getStartDate());
        dto.setEndDate(leaveRequest.getEndDate());
        dto.setTotalDays(leaveRequest.getTotalDays());
        dto.setReason(leaveRequest.getReason());
        dto.setStatus(leaveRequest.getStatus());
        dto.setApprovedBy(leaveRequest.getApprovedBy());
        dto.setApprovedOn(leaveRequest.getApprovedOn());
        dto.setRejectionReason(leaveRequest.getRejectionReason());
        dto.setAttachmentUrl(leaveRequest.getAttachmentUrl());
        dto.setNotes(leaveRequest.getNotes());
        dto.setCreatedAt(leaveRequest.getCreatedAt());
        dto.setCreatedBy(leaveRequest.getCreatedBy());
        return dto;
    }

    public List<LeaveRequestDTO> toDTOList(List<LeaveRequest> leaveRequests) {
        return leaveRequests.stream().map(this::toDTO).toList();
    }
}
