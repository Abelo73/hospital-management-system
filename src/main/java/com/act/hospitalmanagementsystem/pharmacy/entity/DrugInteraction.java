package com.act.hospitalmanagementsystem.pharmacy.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.pharmacy.enums.InteractionSeverity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pharmacy_drug_interactions")
public class DrugInteraction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug1_id")
    private Drug drug1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug2_id")
    private Drug drug2;

    @Column(name = "severity", nullable = false)
    @Enumerated(EnumType.STRING)
    private InteractionSeverity severity;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "effects", columnDefinition = "JSON")
    private String effects;

    @Column(name = "management", columnDefinition = "TEXT")
    private String management;

    @Column(name = "references", columnDefinition = "JSON")
    private String references;
}
