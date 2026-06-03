package com.act.hospitalmanagementsystem.nursing.repository;

import com.act.hospitalmanagementsystem.nursing.entity.NursingNote;
import com.act.hospitalmanagementsystem.nursing.enums.NoteType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NursingNoteRepository extends JpaRepository<NursingNote, UUID> {

    Optional<NursingNote> findByIdAndDeletedFalse(UUID id);

    List<NursingNote> findByPatientIdAndDeletedFalseOrderByNoteDateDescNoteTimeDesc(UUID patientId);

    Page<NursingNote> findByPatientIdAndDeletedFalse(UUID patientId, Pageable pageable);

    Page<NursingNote> findByNoteTypeAndDeletedFalse(NoteType noteType, Pageable pageable);

    Page<NursingNote> findByPatientIdAndNoteTypeAndDeletedFalse(UUID patientId, NoteType noteType, Pageable pageable);

    Page<NursingNote> findByNurseIdAndDeletedFalse(UUID nurseId, Pageable pageable);

    @Query("SELECT n FROM NursingNote n WHERE n.deleted = false AND n.noteDate = :date")
    List<NursingNote> findByNoteDate(@Param("date") java.time.LocalDate date);

    @Query("SELECT n FROM NursingNote n WHERE n.deleted = false AND " +
           "(LOWER(n.subject) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(n.narrative) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<NursingNote> searchNotes(@Param("searchTerm") String searchTerm, Pageable pageable);
}
