package com.act.hospitalmanagementsystem.nursing.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.nursing.dto.CreateNursingNoteRequest;
import com.act.hospitalmanagementsystem.nursing.dto.NursingNoteDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateNursingNoteRequest;
import com.act.hospitalmanagementsystem.nursing.enums.NoteType;
import com.act.hospitalmanagementsystem.nursing.service.NursingNoteService;
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
@RequestMapping("/nursing/notes")
@RequiredArgsConstructor
public class NursingNoteController {

    private final NursingNoteService nursingNoteService;

    @PostMapping
    @PreAuthorize("hasAuthority('NURSING_WRITE')")
    public ResponseEntity<BaseResponseDTO<NursingNoteDTO>> createNursingNote(@Valid @RequestBody CreateNursingNoteRequest request) {
        NursingNoteDTO note = nursingNoteService.createNursingNote(request);
        return ResponseEntity.ok(BaseResponseDTO.success(note));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<NursingNoteDTO>> getNursingNoteById(@PathVariable UUID id) {
        NursingNoteDTO note = nursingNoteService.getNursingNoteById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(note));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<List<NursingNoteDTO>>> getNursingNotesByPatientId(@PathVariable UUID patientId) {
        List<NursingNoteDTO> notes = nursingNoteService.getNursingNotesByPatientId(patientId);
        return ResponseEntity.ok(BaseResponseDTO.success(notes));
    }

    @GetMapping("/patient/{patientId}/paginated")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingNoteDTO>>> getNursingNotesByPatientIdPaginated(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "noteDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingNoteDTO> notes = nursingNoteService.getNursingNotesByPatientId(patientId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(notes));
    }

    @GetMapping("/type/{noteType}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingNoteDTO>>> getNursingNotesByType(
            @PathVariable NoteType noteType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "noteDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingNoteDTO> notes = nursingNoteService.getNursingNotesByType(noteType, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(notes));
    }

    @GetMapping("/patient/{patientId}/type/{noteType}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingNoteDTO>>> getNursingNotesByPatientIdAndType(
            @PathVariable UUID patientId,
            @PathVariable NoteType noteType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "noteDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingNoteDTO> notes = nursingNoteService.getNursingNotesByPatientIdAndType(patientId, noteType, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(notes));
    }

    @GetMapping("/nurse/{nurseId}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingNoteDTO>>> getNursingNotesByNurseId(
            @PathVariable UUID nurseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "noteDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingNoteDTO> notes = nursingNoteService.getNursingNotesByNurseId(nurseId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(notes));
    }

    @GetMapping("/date/{date}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<List<NursingNoteDTO>>> getNursingNotesByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<NursingNoteDTO> notes = nursingNoteService.getNursingNotesByDate(date);
        return ResponseEntity.ok(BaseResponseDTO.success(notes));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingNoteDTO>>> searchNursingNotes(
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "noteDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingNoteDTO> notes = nursingNoteService.searchNursingNotes(searchTerm, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(notes));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_WRITE')")
    public ResponseEntity<BaseResponseDTO<NursingNoteDTO>> updateNursingNote(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateNursingNoteRequest request
    ) {
        NursingNoteDTO note = nursingNoteService.updateNursingNote(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success(note));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteNursingNote(@PathVariable UUID id) {
        nursingNoteService.deleteNursingNote(id);
        return ResponseEntity.ok(BaseResponseDTO.success(null));
    }
}
