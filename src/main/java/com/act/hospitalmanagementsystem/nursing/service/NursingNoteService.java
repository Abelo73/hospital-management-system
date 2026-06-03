package com.act.hospitalmanagementsystem.nursing.service;

import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import com.act.hospitalmanagementsystem.nursing.dto.CreateNursingNoteRequest;
import com.act.hospitalmanagementsystem.nursing.dto.NursingNoteDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateNursingNoteRequest;
import com.act.hospitalmanagementsystem.nursing.entity.NursingNote;
import com.act.hospitalmanagementsystem.nursing.enums.NoteType;
import com.act.hospitalmanagementsystem.nursing.mapper.NursingNoteMapper;
import com.act.hospitalmanagementsystem.nursing.repository.NursingNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class NursingNoteService {

    private final NursingNoteRepository nursingNoteRepository;
    private final NursingNoteMapper nursingNoteMapper;

    public NursingNoteDTO createNursingNote(CreateNursingNoteRequest request) {
        NursingNote note = nursingNoteMapper.toEntity(request);
        note = nursingNoteRepository.save(note);
        return nursingNoteMapper.toDTO(note);
    }

    public NursingNoteDTO getNursingNoteById(UUID id) {
        NursingNote note = nursingNoteRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nursing Note", "id", id));
        return nursingNoteMapper.toDTO(note);
    }

    public List<NursingNoteDTO> getNursingNotesByPatientId(UUID patientId) {
        List<NursingNote> notes = nursingNoteRepository.findByPatientIdAndDeletedFalseOrderByNoteDateDescNoteTimeDesc(patientId);
        return nursingNoteMapper.toDTOList(notes);
    }

    public Page<NursingNoteDTO> getNursingNotesByPatientId(UUID patientId, Pageable pageable) {
        Page<NursingNote> notes = nursingNoteRepository.findByPatientIdAndDeletedFalse(patientId, pageable);
        return notes.map(nursingNoteMapper::toDTO);
    }

    public Page<NursingNoteDTO> getNursingNotesByType(NoteType noteType, Pageable pageable) {
        Page<NursingNote> notes = nursingNoteRepository.findByNoteTypeAndDeletedFalse(noteType, pageable);
        return notes.map(nursingNoteMapper::toDTO);
    }

    public Page<NursingNoteDTO> getNursingNotesByPatientIdAndType(UUID patientId, NoteType noteType, Pageable pageable) {
        Page<NursingNote> notes = nursingNoteRepository.findByPatientIdAndNoteTypeAndDeletedFalse(patientId, noteType, pageable);
        return notes.map(nursingNoteMapper::toDTO);
    }

    public Page<NursingNoteDTO> getNursingNotesByNurseId(UUID nurseId, Pageable pageable) {
        Page<NursingNote> notes = nursingNoteRepository.findByNurseIdAndDeletedFalse(nurseId, pageable);
        return notes.map(nursingNoteMapper::toDTO);
    }

    public List<NursingNoteDTO> getNursingNotesByDate(LocalDate date) {
        List<NursingNote> notes = nursingNoteRepository.findByNoteDate(date);
        return nursingNoteMapper.toDTOList(notes);
    }

    public Page<NursingNoteDTO> searchNursingNotes(String searchTerm, Pageable pageable) {
        Page<NursingNote> notes = nursingNoteRepository.searchNotes(searchTerm, pageable);
        return notes.map(nursingNoteMapper::toDTO);
    }

    public NursingNoteDTO updateNursingNote(UUID id, UpdateNursingNoteRequest request) {
        NursingNote note = nursingNoteRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nursing Note", "id", id));
        nursingNoteMapper.updateEntityFromDTO(request, note);
        note = nursingNoteRepository.save(note);
        return nursingNoteMapper.toDTO(note);
    }

    public void deleteNursingNote(UUID id) {
        NursingNote note = nursingNoteRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nursing Note", "id", id));
        note.setDeleted(true);
        nursingNoteRepository.save(note);
    }
}
