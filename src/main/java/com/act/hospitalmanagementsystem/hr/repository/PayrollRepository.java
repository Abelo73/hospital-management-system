package com.act.hospitalmanagementsystem.hr.repository;

import com.act.hospitalmanagementsystem.hr.entity.Payroll;
import com.act.hospitalmanagementsystem.hr.enums.PayStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, UUID> {

    List<Payroll> findByEmployeeId(UUID employeeId);

    Page<Payroll> findByStatus(PayStatus status, Pageable pageable);

    List<Payroll> findByPayPeriodStartBetween(LocalDate startDate, LocalDate endDate);

    List<Payroll> findByPayDate(LocalDate payDate);
}
