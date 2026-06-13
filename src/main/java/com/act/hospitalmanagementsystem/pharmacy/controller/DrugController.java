package com.act.hospitalmanagementsystem.pharmacy.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.pharmacy.dto.DrugDTO;
import com.act.hospitalmanagementsystem.pharmacy.dto.DrugInteractionCheckDTO;
import com.act.hospitalmanagementsystem.pharmacy.service.DrugService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/pharmacy/drugs")
@RequiredArgsConstructor
public class DrugController {

    private final DrugService drugService;

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('PHARMACY_READ')")
    public ResponseEntity<BaseResponseDTO<List<DrugDTO>>> searchDrugs(
            @RequestParam String query,
            Pageable pageable) {
        BaseResponseDTO<List<DrugDTO>> response = drugService.searchDrugs(query, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{drugId}")
    @PreAuthorize("hasAuthority('PHARMACY_READ')")
    public ResponseEntity<BaseResponseDTO<DrugDTO>> getDrugDetails(@PathVariable UUID drugId) {
        BaseResponseDTO<DrugDTO> response = drugService.getDrugDetails(drugId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/interactions/check")
    @PreAuthorize("hasAuthority('PHARMACY_READ')")
    public ResponseEntity<BaseResponseDTO<DrugInteractionCheckDTO>> checkDrugInteractions(
            @RequestBody Map<String, Object> request) {
        List<String> drugs = (List<String>) request.get("drugs");
        UUID patientId = request.get("patientId") != null ? UUID.fromString((String) request.get("patientId")) : null;

        BaseResponseDTO<DrugInteractionCheckDTO> response = drugService.checkDrugInteractions(drugs, patientId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/controlled-substances")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<BaseResponseDTO<List<DrugDTO>>> getControlledSubstances(Pageable pageable) {
        BaseResponseDTO<List<DrugDTO>> response = drugService.getControlledSubstances(pageable);
        return ResponseEntity.ok(response);
    }
}
