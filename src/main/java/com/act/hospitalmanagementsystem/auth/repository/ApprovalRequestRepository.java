package com.act.hospitalmanagementsystem.auth.repository;

import com.act.hospitalmanagementsystem.auth.entity.ApprovalRequest;
import com.act.hospitalmanagementsystem.auth.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequest, UUID> {

    List<ApprovalRequest> findByUserId(UUID userId);

    @Query("SELECT ar FROM ApprovalRequest ar WHERE ar.deleted = false AND ar.user.id = :userId ORDER BY ar.createdAt DESC")
    List<ApprovalRequest> findByUserIdAndDeletedFalse(@Param("userId") UUID userId);

    @Query("SELECT ar FROM ApprovalRequest ar WHERE ar.deleted = false AND ar.status = :status ORDER BY ar.priority ASC, ar.createdAt ASC")
    List<ApprovalRequest> findByStatus(@Param("status") ApprovalStatus status);

    @Query("SELECT ar FROM ApprovalRequest ar WHERE ar.deleted = false AND ar.status IN :statuses ORDER BY ar.priority ASC, ar.createdAt ASC")
    List<ApprovalRequest> findByStatusIn(@Param("statuses") List<ApprovalStatus> statuses);

    @Query("SELECT ar FROM ApprovalRequest ar WHERE ar.deleted = false AND ar.requestType = :requestType AND ar.status = :status")
    List<ApprovalRequest> findByRequestTypeAndStatus(@Param("requestType") String requestType, @Param("status") ApprovalStatus status);

    @Query("SELECT COUNT(ar) FROM ApprovalRequest ar WHERE ar.deleted = false AND ar.status = :status")
    Long countByStatus(@Param("status") ApprovalStatus status);

    @Query("SELECT ar FROM ApprovalRequest ar WHERE ar.deleted = false AND ar.documentsRequired = true AND ar.documentsVerified = false")
    List<ApprovalRequest> findPendingDocumentVerification();
}
