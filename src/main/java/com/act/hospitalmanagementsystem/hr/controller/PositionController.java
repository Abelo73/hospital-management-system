package com.act.hospitalmanagementsystem.hr.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.PositionDTO;
import com.act.hospitalmanagementsystem.hr.entity.Position;
import com.act.hospitalmanagementsystem.hr.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hr/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @GetMapping
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<List<PositionDTO>>> getAll(
            @RequestParam(required = false) UUID departmentId) {
        return ResponseEntity.ok(positionService.getAll(departmentId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<PositionDTO>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(positionService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<PositionDTO>> create(
            @RequestBody Position position,
            Authentication authentication) {
        return ResponseEntity.ok(positionService.create(position, authentication.getName()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<PositionDTO>> update(
            @PathVariable UUID id,
            @RequestBody Position position,
            Authentication authentication) {
        return ResponseEntity.ok(positionService.update(id, position, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> delete(
            @PathVariable UUID id,
            Authentication authentication) {
        return ResponseEntity.ok(positionService.delete(id, authentication.getName()));
    }
}
