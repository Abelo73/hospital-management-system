package com.act.hospitalmanagementsystem.hr.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.PayrollDTO;
import com.act.hospitalmanagementsystem.hr.entity.Payroll;
import com.act.hospitalmanagementsystem.hr.enums.PayStatus;
import com.act.hospitalmanagementsystem.hr.mapper.PayrollMapper;
import com.act.hospitalmanagementsystem.hr.repository.PayrollRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final PayrollMapper payrollMapper;

    @Transactional
    public BaseResponseDTO<PayrollDTO> processPayroll(UUID employeeId, String payPeriodStart, String payPeriodEnd, 
            Double grossPay, Double taxDeduction, Double insuranceDeduction, Double bonuses, Double overtimePay, String createdBy) {
        try {
            Payroll payroll = new Payroll();
            payroll.setEmployeeId(employeeId);
            payroll.setPayPeriodStart(java.time.LocalDate.parse(payPeriodStart));
            payroll.setPayPeriodEnd(java.time.LocalDate.parse(payPeriodEnd));
            payroll.setGrossPay(grossPay);
            payroll.setTaxDeduction(taxDeduction);
            payroll.setInsuranceDeduction(insuranceDeduction);
            payroll.setBonuses(bonuses);
            payroll.setOvertimePay(overtimePay);
            payroll.setNetPay(grossPay - taxDeduction - insuranceDeduction + bonuses + overtimePay);
            payroll.setStatus(PayStatus.PENDING);
            payroll.setCreatedBy(createdBy);

            Payroll saved = payrollRepository.save(payroll);
            return BaseResponseDTO.success(payrollMapper.toDTO(saved), "Payroll processed successfully");
        } catch (Exception e) {
            log.error("Error processing payroll", e);
            return BaseResponseDTO.error("Failed to process payroll: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<PayrollDTO>> getPayrollRecords(UUID employeeId, PayStatus status, Pageable pageable) {
        try {
            Page<Payroll> payrolls;
            if (status != null) {
                payrolls = payrollRepository.findByStatus(status, pageable);
            } else if (employeeId != null) {
                List<Payroll> list = payrollRepository.findByEmployeeId(employeeId);
                payrolls = new org.springframework.data.domain.PageImpl<>(list, pageable, list.size());
            } else {
                payrolls = payrollRepository.findAll(pageable);
            }
            return BaseResponseDTO.success(payrollMapper.toDTOList(payrolls.getContent()), "Payroll records retrieved");
        } catch (Exception e) {
            log.error("Error getting payroll records", e);
            return BaseResponseDTO.error("Failed to get payroll records: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<PayrollDTO> markAsPaid(UUID id, String paymentMethod, String transactionId, String updatedBy) {
        try {
            Payroll payroll = payrollRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Payroll record not found"));

            payroll.setStatus(PayStatus.PAID);
            payroll.setPaymentMethod(paymentMethod);
            payroll.setTransactionId(transactionId);
            payroll.setPayDate(java.time.LocalDate.now());
            payroll.setUpdatedBy(updatedBy);

            Payroll saved = payrollRepository.save(payroll);
            return BaseResponseDTO.success(payrollMapper.toDTO(saved), "Payroll marked as paid");
        } catch (Exception e) {
            log.error("Error marking payroll as paid", e);
            return BaseResponseDTO.error("Failed to mark payroll as paid: " + e.getMessage());
        }
    }
}
