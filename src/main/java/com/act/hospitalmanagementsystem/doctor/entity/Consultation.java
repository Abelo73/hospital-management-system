package com.act.hospitalmanagementsystem.doctor.entity;

import com.act.hospitalmanagementsystem.auth.entity.User;
import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.patient.entity.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "consultations")
public class Consultation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    @Column(name = "consultation_date", nullable = false)
    private LocalDateTime consultationDate;

    @Column(name = "chief_complaint", length = 1000)
    private String chiefComplaint;

    @Column(name = "history_of_present_illness", columnDefinition = "TEXT")
    private String historyOfPresentIllness;

    @Column(name = "physical_examination", columnDefinition = "TEXT")
    private String physicalExamination;

    @Column(name = "plan", columnDefinition = "TEXT")
    private String plan;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConsultationDiagnosis> diagnoses = new ArrayList<>();

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prescription> prescriptions = new ArrayList<>();

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "is_finalized", nullable = false)
    private Boolean isFinalized = false;

    @Column(name = "finalized_at")
    private LocalDateTime finalizedAt;
}
