package com.act.hospitalmanagementsystem.nursing.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.nursing.dto.CreateNursingShiftRequest;
import com.act.hospitalmanagementsystem.nursing.dto.NursingShiftDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateNursingShiftRequest;
import com.act.hospitalmanagementsystem.nursing.enums.ShiftRecordStatus;
import com.act.hospitalmanagementsystem.nursing.enums.ShiftType;
import com.act.hospitalmanagementsystem.nursing.service.NursingShiftService;
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
@RequestMapping("/nursing/shifts")
@RequiredArgsConstructor
public class NursingShiftController {

    private final NursingShiftService nursingShiftService;

    @PostMapping
    @PreAuthorize("hasAuthority('NURSING_WRITE')")
    public ResponseEntity<BaseResponseDTO<NursingShiftDTO>> createNursingShift(@Valid @RequestBody CreateNursingShiftRequest request) {
        NursingShiftDTO nursingShift = nursingShiftService.createNursingShift(request);
        return ResponseEntity.ok(BaseResponseDTO.success(nursingShift));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<NursingShiftDTO>> getNursingShiftById(@PathVariable UUID id) {
        NursingShiftDTO nursingShift = nursingShiftService.getNursingShiftById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(nursingShift));
    }

    @GetMapping("/date/{shiftDate}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<List<NursingShiftDTO>>> getNursingShiftsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate shiftDate
    ) {
        List<NursingShiftDTO> nursingShifts = nursingShiftService.getNursingShiftsByDate(shiftDate);
        return ResponseEntity.ok(BaseResponseDTO.success(nursingShifts));
    }

    @GetMapping("/date/{shiftDate}/paginated")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingShiftDTO>>> getNursingShiftsByDatePaginated(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate shiftDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "shiftDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingShiftDTO> nursingShifts = nursingShiftService.getNursingShiftsByDate(shiftDate, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(nursingShifts));
    }

    @GetMapping("/nurse/{nurseId}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingShiftDTO>>> getNursingShiftsByNurseId(
            @PathVariable UUID nurseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "shiftDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingShiftDTO> nursingShifts = nursingShiftService.getNursingShiftsByNurseId(nurseId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(nursingShifts));
    }

    @GetMapping("/type/{shiftType}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingShiftDTO>>> getNursingShiftsByType(
            @PathVariable ShiftType shiftType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "shiftDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingShiftDTO> nursingShifts = nursingShiftService.getNursingShiftsByType(shiftType, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(nursingShifts));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingShiftDTO>>> getNursingShiftsByStatus(
            @PathVariable ShiftRecordStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "shiftDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingShiftDTO> nursingShifts = nursingShiftService.getNursingShiftsByStatus(status, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(nursingShifts));
    }

    @GetMapping("/unit/{unit}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingShiftDTO>>> getNursingShiftsByUnit(
            @PathVariable String unit,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "shiftDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingShiftDTO> nursingShifts = nursingShiftService.getNursingShiftsByUnit(unit, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(nursingShifts));
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingShiftDTO>>> getNursingShiftsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "shiftDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingShiftDTO> nursingShifts = nursingShiftService.getNursingShiftsByDateRange(startDate, endDate, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(nursingShifts));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_WRITE')")
    public ResponseEntity<BaseResponseDTO<NursingShiftDTO>> updateNursingShift(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateNursingShiftRequest request
    ) {
        NursingShiftDTO nursingShift = nursingShiftService.updateNursingShift(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success(nursingShift));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteNursingShift(@PathVariable UUID id) {
        nursingShiftService.deleteNursingShift(id);
        return ResponseEntity.ok(BaseResponseDTO.success(null));
    }
}
