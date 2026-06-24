package com.act.hospitalmanagementsystem.hr.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.LeaveBalanceDTO;
import com.act.hospitalmanagementsystem.hr.entity.LeaveBalance;
import com.act.hospitalmanagementsystem.hr.entity.LeaveType;
import com.act.hospitalmanagementsystem.hr.repository.LeaveBalanceRepository;
import com.act.hospitalmanagementsystem.hr.repository.LeaveTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeaveBalanceService {

    private final LeaveBalanceRepository leaveBalanceRepository;
    private final LeaveTypeRepository leaveTypeRepository;

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<LeaveBalanceDTO>> getBalancesForEmployee(UUID employeeId, Integer year) {
        try {
            int targetYear = year != null ? year : LocalDate.now().getYear();
            List<LeaveBalance> balances = leaveBalanceRepository.findByEmployeeIdAndLeaveCycleYear(employeeId, targetYear);
            return BaseResponseDTO.success("Leave balances retrieved",
                    balances.stream().map(this::toDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error("Error getting leave balances", e);
            return BaseResponseDTO.error("Failed to get leave balances: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<LeaveBalanceDTO> upsertBalance(UUID employeeId, UUID leaveTypeId, Integer year,
            Integer entitledDays, Integer carriedForwardDays, String createdBy) {
        try {
            int targetYear = year != null ? year : LocalDate.now().getYear();
            LeaveBalance balance = leaveBalanceRepository
                    .findByEmployeeIdAndLeaveTypeIdAndLeaveCycleYear(employeeId, leaveTypeId, targetYear)
                    .orElseGet(() -> {
                        LeaveBalance nb = new LeaveBalance();
                        nb.setEmployeeId(employeeId);
                        nb.setLeaveTypeId(leaveTypeId);
                        nb.setLeaveCycleYear(targetYear);
                        nb.setUsedDays(0);
                        nb.setCreatedBy(createdBy);
                        return nb;
                    });

            if (entitledDays != null) balance.setEntitledDays(entitledDays);
            if (carriedForwardDays != null) balance.setCarriedForwardDays(carriedForwardDays);
            balance.setUpdatedBy(createdBy);

            LeaveBalance saved = leaveBalanceRepository.save(balance);
            return BaseResponseDTO.success("Leave balance saved", toDTO(saved));
        } catch (Exception e) {
            log.error("Error upserting leave balance", e);
            return BaseResponseDTO.error("Failed to save leave balance: " + e.getMessage());
        }
    }

    /** Initialise balances for all active leave types for the current year. */
    @Transactional
    public BaseResponseDTO<Void> initializeBalancesForEmployee(UUID employeeId, Integer year, String createdBy) {
        try {
            int targetYear = year != null ? year : LocalDate.now().getYear();
            List<LeaveType> activeTypes = leaveTypeRepository.findByIsActiveTrue();
            for (LeaveType lt : activeTypes) {
                leaveBalanceRepository
                        .findByEmployeeIdAndLeaveTypeIdAndLeaveCycleYear(employeeId, lt.getId(), targetYear)
                        .orElseGet(() -> {
                            LeaveBalance nb = new LeaveBalance();
                            nb.setEmployeeId(employeeId);
                            nb.setLeaveTypeId(lt.getId());
                            nb.setLeaveCycleYear(targetYear);
                            nb.setEntitledDays(lt.getAnnualDays());
                            nb.setUsedDays(0);
                            nb.setCarriedForwardDays(0);
                            nb.setCreatedBy(createdBy);
                            return leaveBalanceRepository.save(nb);
                        });
            }
            return BaseResponseDTO.<Void>success("Balances initialized", null);
        } catch (Exception e) {
            log.error("Error initializing leave balances", e);
            return BaseResponseDTO.error("Failed to initialize leave balances: " + e.getMessage());
        }
    }

    private LeaveBalanceDTO toDTO(LeaveBalance lb) {
        LeaveBalanceDTO dto = new LeaveBalanceDTO();
        dto.setId(lb.getId());
        dto.setEmployeeId(lb.getEmployeeId());
        dto.setLeaveTypeId(lb.getLeaveTypeId());
        dto.setLeaveCycleYear(lb.getLeaveCycleYear());
        dto.setEntitledDays(lb.getEntitledDays());
        dto.setUsedDays(lb.getUsedDays());
        dto.setCarriedForwardDays(lb.getCarriedForwardDays());
        dto.setRemainingDays(lb.getRemainingDays());
        dto.setCreatedAt(lb.getCreatedAt());
        dto.setCreatedBy(lb.getCreatedBy());
        return dto;
    }
}
