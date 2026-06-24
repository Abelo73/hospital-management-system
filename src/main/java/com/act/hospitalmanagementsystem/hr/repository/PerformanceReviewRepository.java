package com.act.hospitalmanagementsystem.hr.repository;

import com.act.hospitalmanagementsystem.hr.entity.PerformanceReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, UUID> {

    Page<PerformanceReview> findByEmployeeId(UUID employeeId, Pageable pageable);

    Page<PerformanceReview> findByReviewerId(UUID reviewerId, Pageable pageable);

    List<PerformanceReview> findByEmployeeId(UUID employeeId);

    List<PerformanceReview> findByReviewerId(UUID reviewerId);

    @Query("SELECT r FROM PerformanceReview r WHERE r.reviewPeriodStart >= :startDate AND r.reviewPeriodEnd <= :endDate")
    Page<PerformanceReview> findByReviewPeriodBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

    @Query("SELECT r FROM PerformanceReview r WHERE r.reviewPeriodStart >= :startDate AND r.reviewPeriodEnd <= :endDate")
    List<PerformanceReview> findByReviewPeriodBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
