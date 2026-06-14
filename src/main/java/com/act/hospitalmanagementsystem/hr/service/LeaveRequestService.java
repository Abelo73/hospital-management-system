package com.act.hospitalmanagementsystem.hr.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.LeaveRequestDTO;
import com.act.hospitalmanagementsystem.hr.entity.LeaveRequest;
import com.act.hospitalmanagementsystem.hr.enums.LeaveStatus;
import com.act.hospitalmanagementsystem.hr.enums.LeaveType;
import com.act.hospitalmanagementsystem.hr.mapper.LeaveRequestMapper;
import com.act.hospitalmanagementsystem.hr.repository.LeaveRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveRequestMapper leaveRequestMapper;

    @Transactional
    public BaseResponseDTO<LeaveRequestDTO> createLeaveRequest(UUID employeeId, LeaveType leaveType, 
            String startDate, String endDate, String reason, String createdBy) {
        try {
            LeaveRequest leaveRequest = new LeaveRequest();
            leaveRequest.setEmployeeId(employeeId);
            leaveRequest.setLeaveType(leaveType);
            leaveRequest.setStartDate(java.time.LocalDate.parse(startDate));
            leaveRequest.setEndDate(java.time.LocalDate.parse(endDate));
            leaveRequest.setTotalDays((int) ChronoUnit.DAYS.between(
                    java.time.LocalDate.parse(startDate), java.time.LocalDate.parse(endDate)) + 1);
            leaveRequest.setReason(reason);
            leaveRequest.setStatus(LeaveStatus.PENDING);
            leaveRequest.setCreatedBy(createdBy);

            LeaveRequest saved = leaveRequestRepository.save(leaveRequest);
            return BaseResponseDTO.success(leaveRequestMapper.toDTO(saved), "Leave request created successfully");
        } catch (Exception e) {
            log.error("Error creating leave request", e);
            return BaseResponseDTO.error("Failed to create leave request: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<LeaveRequestDTO>> getLeaveRequests(UUID employeeId, LeaveStatus status, LeaveType leaveType, Pageable pageable) {
        try {
            Page<LeaveRequest> leaveRequests;
            if (status != null) {
                leaveRequests = leaveRequestRepository.findByStatus(status, pageable);
            } else if (leaveType != null) {
                leaveRequests = leaveRequestRepository.findByLeaveType(leaveType, pageable);
            } else if (employeeId != null) {
                leaveRequests = leaveRequestRepository.findByEmployeeId(employeeId).stream()
                        .skip(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .collect(java.util.stream.Collectors.toList())
                        .stream()
                        .collect(java.util.stream.Collectors.collectingAndThen(
                                java.util.stream.Collectors.toList(),
                                list -> new org.springframework.data.domain.PageImpl<>(list, pageable, list.size())));
            } else {
                leaveRequests = leaveRequestRepository.findAll(pageable);
            }
            return BaseResponseDTO.success(leaveRequestMapper.toDTOList(leaveRequests.getContent()), "Leave requests retrieved");
        } catch (Exception e) {
            log.error("Error getting leave requests", e);
            return BaseResponseDTO.error("Failed to get leave requests: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<LeaveRequestDTO> approveLeaveRequest(UUID id, Boolean approved, String rejectionReason, String approvedBy) {
        try {
            LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Leave request not found"));

            if (approved) {
                leaveRequest.setStatus(LeaveStatus.APPROVED);
                leaveRequest.setApprovedBy(UUID.fromString(approvedBy));
                leaveRequest.setApprovedOn(java.time.LocalDateTime.now());
            } else {
                leaveRequest.setStatus(LeaveStatus.REJECTED);
                leaveRequest.setRejectionReason(rejectionReason);
            }
            leaveRequest.setUpdatedBy(approvedBy);

            LeaveRequest saved = leaveRequestRepository.save(leaveRequest);
            return BaseResponseDTO.success(leaveRequestMapper.toDTO(saved), approved ? "Leave request approved" : "Leave request rejected");
        } catch (Exception e) {
            log.error("Error approving leave request", e);
            return BaseResponseDTO.error("Failed to approve leave request: " + e.getMessage());
        }
    }
}
