package com.act.hospitalmanagementsystem.notification.repository;

import com.act.hospitalmanagementsystem.notification.entity.UserPreference;
import com.act.hospitalmanagementsystem.notification.enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PreferenceRepository extends JpaRepository<UserPreference, UUID> {

    Optional<UserPreference> findByUserIdAndNotificationType(UUID userId, NotificationType notificationType);

    List<UserPreference> findByUserId(UUID userId);

    List<UserPreference> findByUserIdAndEmailEnabledTrue(UUID userId);

    List<UserPreference> findByUserIdAndSmsEnabledTrue(UUID userId);

    List<UserPreference> findByUserIdAndPushEnabledTrue(UUID userId);

    List<UserPreference> findByUserIdAndInAppEnabledTrue(UUID userId);
}
