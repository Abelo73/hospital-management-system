package com.act.hospitalmanagementsystem.billing.repository;

import com.act.hospitalmanagementsystem.billing.entity.Payment;
import com.act.hospitalmanagementsystem.billing.enums.PaymentMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByInvoiceIdAndDeletedFalse(UUID invoiceId);
    Page<Payment> findByDeletedFalse(Pageable pageable);
    Page<Payment> findByPatientIdAndDeletedFalse(UUID patientId, Pageable pageable);
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.invoice.id = :invoiceId AND p.status = 'COMPLETED' AND p.deleted = false")
    BigDecimal sumPaidByInvoice(@Param("invoiceId") UUID invoiceId);
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.paymentDate = :date AND p.status = 'COMPLETED' AND p.deleted = false")
    BigDecimal sumRevenueByDate(@Param("date") LocalDate date);
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.paymentDate = :date AND p.paymentMethod = :method AND p.status = 'COMPLETED' AND p.deleted = false")
    BigDecimal sumRevenueByDateAndMethod(@Param("date") LocalDate date, @Param("method") PaymentMethod method);
    @Query("SELECT COUNT(p) FROM Payment p WHERE p.paymentDate = :date AND p.status = 'COMPLETED' AND p.deleted = false")
    Long countByDate(@Param("date") LocalDate date);
    @Query("SELECT p FROM Payment p WHERE p.paymentDate BETWEEN :start AND :end AND p.deleted = false")
    List<Payment> findByDateRange(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
