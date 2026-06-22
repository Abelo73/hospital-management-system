package com.act.hospitalmanagementsystem.hr.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "hr_leave_balances",
    uniqueConstraints = @UniqueConstraint(columnNames = {"employee_id", "leave_type_id", "leave_cycle_year"}))
public class LeaveBalance extends BaseEntity {

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "leave_type_id", nullable = false)
    private UUID leaveTypeId;

    @Column(name = "leave_cycle_year", nullable = false)
    private Integer leaveCycleYear;

    @Column(name = "entitled_days", nullable = false)
    private Integer entitledDays = 0;

    @Column(name = "used_days", nullable = false)
    private Integer usedDays = 0;

    @Column(name = "carried_forward_days", nullable = false)
    private Integer carriedForwardDays = 0;

    public int getRemainingDays() {
        return (entitledDays != null ? entitledDays : 0)
             + (carriedForwardDays != null ? carriedForwardDays : 0)
             - (usedDays != null ? usedDays : 0);
    }
}
