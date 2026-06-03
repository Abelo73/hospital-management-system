package com.act.hospitalmanagementsystem.nursing.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.nursing.dto.CreateFluidBalanceRequest;
import com.act.hospitalmanagementsystem.nursing.dto.FluidBalanceDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateFluidBalanceRequest;
import com.act.hospitalmanagementsystem.nursing.enums.ShiftType;
import com.act.hospitalmanagementsystem.nursing.service.FluidBalanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/nursing/fluid-balance")
@RequiredArgsConstructor
public class FluidBalanceController {

    private final FluidBalanceService fluidBalanceService;

    @PostMapping
    @PreAuthorize("hasAuthority('NURSING_WRITE')")
    public ResponseEntity<BaseResponseDTO<FluidBalanceDTO>> createFluidBalance(@Valid @RequestBody CreateFluidBalanceRequest request) {
        FluidBalanceDTO fluidBalance = fluidBalanceService.createFluidBalance(request);
        return ResponseEntity.ok(BaseResponseDTO.success(fluidBalance));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<FluidBalanceDTO>> getFluidBalanceById(@PathVariable UUID id) {
        FluidBalanceDTO fluidBalance = fluidBalanceService.getFluidBalanceById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(fluidBalance));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<List<FluidBalanceDTO>>> getFluidBalanceByPatientId(@PathVariable UUID patientId) {
        List<FluidBalanceDTO> fluidBalanceList = fluidBalanceService.getFluidBalanceByPatientId(patientId);
        return ResponseEntity.ok(BaseResponseDTO.success(fluidBalanceList));
    }

    @GetMapping("/patient/{patientId}/paginated")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<FluidBalanceDTO>>> getFluidBalanceByPatientIdPaginated(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "recordDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<FluidBalanceDTO> fluidBalanceList = fluidBalanceService.getFluidBalanceByPatientId(patientId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(fluidBalanceList));
    }

    @GetMapping("/shift/{shift}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<FluidBalanceDTO>>> getFluidBalanceByShift(
            @PathVariable ShiftType shift,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "recordDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<FluidBalanceDTO> fluidBalanceList = fluidBalanceService.getFluidBalanceByShift(shift, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(fluidBalanceList));
    }

    @GetMapping("/patient/{patientId}/shift/{shift}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<FluidBalanceDTO>>> getFluidBalanceByPatientIdAndShift(
            @PathVariable UUID patientId,
            @PathVariable ShiftType shift,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "recordDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<FluidBalanceDTO> fluidBalanceList = fluidBalanceService.getFluidBalanceByPatientIdAndShift(patientId, shift, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(fluidBalanceList));
    }

    @GetMapping("/date/{date}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<List<FluidBalanceDTO>>> getFluidBalanceByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<FluidBalanceDTO> fluidBalanceList = fluidBalanceService.getFluidBalanceByDate(date);
        return ResponseEntity.ok(BaseResponseDTO.success(fluidBalanceList));
    }

    @GetMapping("/patient/{patientId}/date-range")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<FluidBalanceDTO>>> getFluidBalanceByDateRange(
            @PathVariable UUID patientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "recordDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<FluidBalanceDTO> fluidBalanceList = fluidBalanceService.getFluidBalanceByDateRange(patientId, startDate, endDate, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(fluidBalanceList));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_WRITE')")
    public ResponseEntity<BaseResponseDTO<FluidBalanceDTO>> updateFluidBalance(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateFluidBalanceRequest request
    ) {
        FluidBalanceDTO fluidBalance = fluidBalanceService.updateFluidBalance(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success(fluidBalance));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteFluidBalance(@PathVariable UUID id) {
        fluidBalanceService.deleteFluidBalance(id);
        return ResponseEntity.ok(BaseResponseDTO.success(null));
    }
}
