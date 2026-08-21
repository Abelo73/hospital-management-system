package com.act.hospitalmanagementsystem.tenant.repository;

import com.act.hospitalmanagementsystem.tenant.entity.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<TenantEntity, String> {
    Optional<TenantEntity> findBySubdomain(String subdomain);
    boolean existsBySubdomain(String subdomain);
}
