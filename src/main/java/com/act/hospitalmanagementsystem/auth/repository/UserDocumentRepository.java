package com.act.hospitalmanagementsystem.auth.repository;

import com.act.hospitalmanagementsystem.auth.entity.UserDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDocumentRepository extends JpaRepository<UserDocument, UUID> {

    List<UserDocument> findByUserId(UUID userId);

    List<UserDocument> findByUserIdAndDeletedFalse(UUID userId);

    @Query("SELECT d FROM UserDocument d WHERE d.deleted = false AND d.user.id = :userId AND d.documentType = :documentType")
    Optional<UserDocument> findByUserIdAndDocumentType(@Param("userId") UUID userId, @Param("documentType") String documentType);

    @Query("SELECT d FROM UserDocument d WHERE d.deleted = false AND d.expiryDate < CURRENT_DATE")
    List<UserDocument> findExpiredDocuments();

    @Query("SELECT d FROM UserDocument d WHERE d.deleted = false AND d.expiryDate BETWEEN CURRENT_DATE AND :date")
    List<UserDocument> findDocumentsExpiringBefore(@Param("date") java.time.LocalDate date);

    @Query("SELECT d FROM UserDocument d WHERE d.deleted = false AND d.documentStatus = 'PENDING_VERIFICATION'")
    List<UserDocument> findPendingVerificationDocuments();
}
