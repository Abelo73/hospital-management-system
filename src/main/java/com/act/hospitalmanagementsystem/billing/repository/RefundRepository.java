package com.act.hospitalmanagementsystem.billing.repository;

import com.act.hospitalmanagementsystem.billing.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface RefundRepository extends JpaRepository<Refund, UUID> {
    List<Refund> findByInvoiceIdAndDeletedFalse(UUID invoiceId);
    List<Refund> findByPatientIdAndDeletedFalse(UUID patientId);
}
