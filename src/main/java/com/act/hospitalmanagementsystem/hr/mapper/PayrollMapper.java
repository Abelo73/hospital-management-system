package com.act.hospitalmanagementsystem.hr.mapper;

import com.act.hospitalmanagementsystem.hr.dto.PayrollDTO;
import com.act.hospitalmanagementsystem.hr.entity.Payroll;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PayrollMapper {

    public PayrollDTO toDTO(Payroll payroll) {
        if (payroll == null) {
            return null;
        }

        PayrollDTO dto = new PayrollDTO();
        dto.setId(payroll.getId());
        dto.setEmployeeId(payroll.getEmployeeId());
        dto.setPayPeriodStart(payroll.getPayPeriodStart());
        dto.setPayPeriodEnd(payroll.getPayPeriodEnd());
        dto.setPayDate(payroll.getPayDate());
        dto.setGrossPay(payroll.getGrossPay());
        dto.setNetPay(payroll.getNetPay());
        dto.setTaxDeduction(payroll.getTaxDeduction());
        dto.setInsuranceDeduction(payroll.getInsuranceDeduction());
        dto.setOtherDeductions(payroll.getOtherDeductions());
        dto.setBonuses(payroll.getBonuses());
        dto.setOvertimePay(payroll.getOvertimePay());
        dto.setStatus(payroll.getStatus());
        dto.setPaymentMethod(payroll.getPaymentMethod());
        dto.setTransactionId(payroll.getTransactionId());
        dto.setNotes(payroll.getNotes());
        dto.setCreatedAt(payroll.getCreatedAt());
        dto.setCreatedBy(payroll.getCreatedBy());
        return dto;
    }

    public List<PayrollDTO> toDTOList(List<Payroll> payrolls) {
        return payrolls.stream().map(this::toDTO).toList();
    }
}
