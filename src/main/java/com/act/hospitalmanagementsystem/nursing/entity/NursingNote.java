package com.act.hospitalmanagementsystem.nursing.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.nursing.enums.NoteType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "nursing_notes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NursingNote extends BaseEntity {

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "admission_id")
    private UUID admissionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "note_type", nullable = false, length = 50)
    private NoteType noteType;

    @Column(name = "note_date", nullable = false)
    private LocalDate noteDate;

    @Column(name = "note_time", nullable = false)
    private LocalTime noteTime;

    @Column(name = "nurse_id")
    private UUID nurseId;

    @Column(name = "subject", length = 200)
    private String subject;

    @Column(name = "narrative", columnDefinition = "TEXT")
    private String narrative;

    @Column(name = "care_provided", columnDefinition = "TEXT")
    private String careProvided;

    @Column(name = "patient_response", length = 500)
    private String patientResponse;

    @Column(name = "follow_up_required")
    private Boolean followUpRequired;

    @Column(name = "follow_up_action", length = 500)
    private String followUpAction;

    @Column(name = "is_confidential")
    private Boolean isConfidential;

    @Column(name = "attachments", columnDefinition = "TEXT")
    private String attachments;
}
