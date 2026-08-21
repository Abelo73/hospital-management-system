package com.act.hospitalmanagementsystem.tenant.service;

import com.act.hospitalmanagementsystem.tenant.entity.TenantEntity;
import com.act.hospitalmanagementsystem.tenant.repository.TenantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SuperAdminTenantService {

    private static final Logger log = LoggerFactory.getLogger(SuperAdminTenantService.class);
    private final TenantRepository tenantRepository;

    public SuperAdminTenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public List<TenantEntity> getAllTenants() {
        List<TenantEntity> list = tenantRepository.findAll();
        if (list.isEmpty()) {
            // Seed initial demo hospital tenants if database is empty
            seedInitialTenants();
            return tenantRepository.findAll();
        }
        return list;
    }

    @Transactional
    public TenantEntity createTenant(String name, String subdomain, String adminEmail, String plan) {
        String tenantId = "tenant-" + UUID.randomUUID().toString().substring(0, 8);
        
        String cleanSubdomain = (subdomain != null && !subdomain.isBlank()) 
                ? subdomain.toLowerCase().replaceAll("[^a-z0-9]", "") 
                : name.toLowerCase().replaceAll("[^a-z0-9]", "");
        
        cleanSubdomain = cleanSubdomain + ".hms-cloud.com";

        TenantEntity tenant = new TenantEntity(tenantId, name, cleanSubdomain, adminEmail, plan != null ? plan : "PRO", "ACTIVE");
        tenant.setCreatedAt(LocalDateTime.now());
        tenant.setUsersCount(1);
        tenant.setPatientsCount(0);

        TenantEntity saved = tenantRepository.save(tenant);
        log.info("Provisioned new hospital tenant: {} ({}) with plan {}", name, cleanSubdomain, plan);
        return saved;
    }

    @Transactional
    public TenantEntity toggleTenantStatus(String tenantId) {
        TenantEntity tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + tenantId));

        String nextStatus = "ACTIVE".equalsIgnoreCase(tenant.getStatus()) ? "SUSPENDED" : "ACTIVE";
        tenant.setStatus(nextStatus);
        
        TenantEntity updated = tenantRepository.save(tenant);
        log.info("Tenant {} status toggled to {}", tenantId, nextStatus);
        return updated;
    }

    private void seedInitialTenants() {
        log.info("Seeding initial SaaS hospital tenants...");
        tenantRepository.save(new TenantEntity("tenant-1", "St. Mary Regional Medical Center", "stmarys.hms-cloud.com", "admin@stmarys-health.org", "ULTRA", "ACTIVE"));
        tenantRepository.save(new TenantEntity("tenant-2", "City Care Outpatient Clinic", "citycare.hms-cloud.com", "dr.smith@citycare-clinic.com", "PRO", "ACTIVE"));
        tenantRepository.save(new TenantEntity("tenant-3", "Metro Dental & Surgical Suite", "metrodental.hms-cloud.com", "office@metrodental.org", "FREE", "ACTIVE"));
    }
}
