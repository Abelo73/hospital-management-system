package com.act.hospitalmanagementsystem.billing.repository;

import com.act.hospitalmanagementsystem.billing.entity.Invoice;
import com.act.hospitalmanagementsystem.billing.enums.InvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    Optional<Invoice> findByInvoiceNumberAndDeletedFalse(String invoiceNumber);
    Page<Invoice> findByDeletedFalse(Pageable pageable);
    Page<Invoice> findByPatientIdAndDeletedFalse(UUID patientId, Pageable pageable);
    Page<Invoice> findByStatusAndDeletedFalse(InvoiceStatus status, Pageable pageable);
    List<Invoice> findByPatientIdAndDeletedFalse(UUID patientId);
    @Query("SELECT i FROM Invoice i WHERE i.status = 'PENDING' AND i.dueDate < :today AND i.deleted = false")
    List<Invoice> findOverdueInvoices(@Param("today") LocalDate today);
    @Query("SELECT i FROM Invoice i WHERE i.invoiceDate BETWEEN :start AND :end AND i.deleted = false")
    List<Invoice> findByDateRange(@Param("start") LocalDate start, @Param("end") LocalDate end);
    @Query("SELECT COALESCE(MAX(CAST(SUBSTRING(i.invoiceNumber, 9) AS integer)), 0) FROM Invoice i WHERE i.invoiceNumber LIKE 'INV-%'")
    Integer findMaxInvoiceSequence();
}
