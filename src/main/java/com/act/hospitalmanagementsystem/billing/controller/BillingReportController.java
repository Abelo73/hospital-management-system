package com.act.hospitalmanagementsystem.billing.controller;

import com.act.hospitalmanagementsystem.billing.dto.InvoiceDTO;
import com.act.hospitalmanagementsystem.billing.service.BillingReportService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/billing/reports")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('BILLING_READ')")
public class BillingReportController {

    private final BillingReportService reportService;

    @GetMapping("/daily-revenue")
    public ResponseEntity<BaseResponseDTO<Map<String, Object>>> getDailyRevenue(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(BaseResponseDTO.success(reportService.getDailyRevenue(date != null ? date : LocalDate.now())));
    }

    @GetMapping("/monthly-summary")
    public ResponseEntity<BaseResponseDTO<Map<String, Object>>> getMonthlySummary(
            @RequestParam(defaultValue = "0") int year,
            @RequestParam(defaultValue = "0") int month) {
        LocalDate now = LocalDate.now();
        return ResponseEntity.ok(BaseResponseDTO.success(
                reportService.getMonthlySummary(year > 0 ? year : now.getYear(), month > 0 ? month : now.getMonthValue())));
    }

    @GetMapping("/outstanding")
    public ResponseEntity<BaseResponseDTO<List<InvoiceDTO>>> getOutstandingBalances() {
        return ResponseEntity.ok(BaseResponseDTO.success(reportService.getOutstandingBalances()));
    }

    @GetMapping("/aging")
    public ResponseEntity<BaseResponseDTO<List<InvoiceDTO>>> getAgingReport() {
        return ResponseEntity.ok(BaseResponseDTO.success(reportService.getAgingReport()));
    }
}
