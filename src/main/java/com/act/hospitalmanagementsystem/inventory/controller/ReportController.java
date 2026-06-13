package com.act.hospitalmanagementsystem.inventory.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.inventory.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/inventory/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/stock")
    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    public ResponseEntity<BaseResponseDTO<Map<String, Object>>> generateStockReport(
            @RequestBody Map<String, String> request) {
        String location = request.get("location");
        String category = request.get("category");
        String format = request.get("format");

        BaseResponseDTO<Map<String, Object>> response = reportService.generateStockReport(location, category, format);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/movement")
    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    public ResponseEntity<BaseResponseDTO<Map<String, Object>>> generateMovementReport(
            @RequestBody Map<String, String> request) {
        String startDate = request.get("startDate");
        String endDate = request.get("endDate");
        String itemType = request.get("itemType");
        String format = request.get("format");

        BaseResponseDTO<Map<String, Object>> response = reportService.generateMovementReport(startDate, endDate, itemType, format);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/consumption")
    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    public ResponseEntity<BaseResponseDTO<Map<String, Object>>> generateConsumptionReport(
            @RequestBody Map<String, String> request) {
        String startDate = request.get("startDate");
        String endDate = request.get("endDate");
        String department = request.get("department");
        String format = request.get("format");

        BaseResponseDTO<Map<String, Object>> response = reportService.generateConsumptionReport(startDate, endDate, department, format);
        return ResponseEntity.ok(response);
    }
}
