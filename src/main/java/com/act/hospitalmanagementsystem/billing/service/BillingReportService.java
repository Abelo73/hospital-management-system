package com.act.hospitalmanagementsystem.billing.service;

import com.act.hospitalmanagementsystem.billing.dto.InvoiceDTO;
import com.act.hospitalmanagementsystem.billing.enums.InvoiceStatus;
import com.act.hospitalmanagementsystem.billing.enums.PaymentMethod;
import com.act.hospitalmanagementsystem.billing.mapper.BillingMapper;
import com.act.hospitalmanagementsystem.billing.repository.InvoiceRepository;
import com.act.hospitalmanagementsystem.billing.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillingReportService {

    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final BillingMapper billingMapper;

    public Map<String, Object> getDailyRevenue(LocalDate date) {
        Map<String, Object> report = new LinkedHashMap<>();
        report.put("date", date);
        report.put("totalRevenue", paymentRepository.sumRevenueByDate(date));
        report.put("cashPayments", paymentRepository.sumRevenueByDateAndMethod(date, PaymentMethod.CASH));
        report.put("cardPayments", paymentRepository.sumRevenueByDateAndMethod(date, PaymentMethod.CREDIT_CARD));
        report.put("insurancePayments", paymentRepository.sumRevenueByDateAndMethod(date, PaymentMethod.INSURANCE));
        report.put("paymentCount", paymentRepository.countByDate(date));
        return report;
    }

    public Map<String, Object> getMonthlySummary(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("year", year);
        summary.put("month", month);
        BigDecimal total = paymentRepository.findByDateRange(start, end).stream()
                .map(p -> p.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        summary.put("totalRevenue", total);
        summary.put("invoiceCount", invoiceRepository.findByDateRange(start, end).size());
        return summary;
    }

    public List<InvoiceDTO> getOutstandingBalances() {
        return invoiceRepository.findByStatusAndDeletedFalse(InvoiceStatus.PENDING, org.springframework.data.domain.Pageable.unpaged())
                .getContent().stream().map(billingMapper::toDTO).collect(Collectors.toList());
    }

    public List<InvoiceDTO> getAgingReport() {
        return invoiceRepository.findOverdueInvoices(LocalDate.now())
                .stream().map(billingMapper::toDTO).collect(Collectors.toList());
    }
}
