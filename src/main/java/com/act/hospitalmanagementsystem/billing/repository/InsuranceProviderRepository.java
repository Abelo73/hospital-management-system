package com.act.hospitalmanagementsystem.billing.repository;

import com.act.hospitalmanagementsystem.billing.entity.InsuranceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface InsuranceProviderRepository extends JpaRepository<InsuranceProvider, UUID> {
    List<InsuranceProvider> findByIsActiveTrueAndDeletedFalse();
    List<InsuranceProvider> findByDeletedFalse();
}
