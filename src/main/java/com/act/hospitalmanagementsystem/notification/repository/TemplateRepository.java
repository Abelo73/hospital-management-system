package com.act.hospitalmanagementsystem.notification.repository;

import com.act.hospitalmanagementsystem.notification.entity.Template;
import com.act.hospitalmanagementsystem.notification.enums.NotificationType;
import com.act.hospitalmanagementsystem.notification.enums.TemplateType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TemplateRepository extends JpaRepository<Template, UUID> {

    Optional<Template> findByTemplateCode(String templateCode);

    Page<Template> findByTemplateType(TemplateType templateType, Pageable pageable);

    Page<Template> findByNotificationType(NotificationType notificationType, Pageable pageable);

    @Query("SELECT t FROM Template t WHERE t.deleted = false AND t.isActive = true AND t.templateType = :type AND t.notificationType = :notificationType")
    Optional<Template> findActiveTemplateByTypeAndNotificationType(@Param("type") TemplateType type, @Param("notificationType") NotificationType notificationType);

    Page<Template> findByIsActiveTrue(Pageable pageable);
}
