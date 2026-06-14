package com.act.hospitalmanagementsystem.hr.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.PayrollDTO;
import com.act.hospitalmanagementsystem.hr.enums.PayStatus;
import com.act.hospitalmanagementsystem.hr.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/hr/payroll")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;

    @PostMapping("/process")
    @PreAuthorize("hasAuthority('HR_ADMIN')")
    public ResponseEntity<BaseResponseDTO<PayrollDTO>> processPayroll(
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        UUID employeeId = UUID.fromString((String) request.get("employeeId"));
        String payPeriodStart = (String) request.get("payPeriodStart");
        String payPeriodEnd = (String) request.get("payPeriodEnd");
        Double grossPay = (Double) request.get("grossPay");
        Double taxDeduction = (Double) request.get("taxDeduction");
        Double insuranceDeduction = (Double) request.get("insuranceDeduction");
        Double bonuses = (Double) request.get("bonuses");
        Double overtimePay = (Double) request.get("overtimePay");
        String createdBy = authentication.getName();

        BaseResponseDTO<PayrollDTO> response = payrollService.processPayroll(
                employeeId, payPeriodStart, payPeriodEnd, grossPay, taxDeduction, insuranceDeduction, bonuses, overtimePay, createdBy);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<List<PayrollDTO>>> getPayrollRecords(
            @RequestParam(required = false) UUID employeeId,
            @RequestParam(required = false) PayStatus status,
            Pageable pageable) {
        BaseResponseDTO<List<PayrollDTO>> response = payrollService.getPayrollRecords(employeeId, status, pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/mark-paid")
    @PreAuthorize("hasAuthority('HR_ADMIN')")
    public ResponseEntity<BaseResponseDTO<PayrollDTO>> markAsPaid(
            @PathVariable UUID id,
            @RequestBody Map<String, String> request,
            Authentication authentication) {
        String paymentMethod = request.get("paymentMethod");
        String transactionId = request.get("transactionId");
        String updatedBy = authentication.getName();

        BaseResponseDTO<PayrollDTO> response = payrollService.markAsPaid(id, paymentMethod, transactionId, updatedBy);
        return ResponseEntity.ok(response);
    }
}
