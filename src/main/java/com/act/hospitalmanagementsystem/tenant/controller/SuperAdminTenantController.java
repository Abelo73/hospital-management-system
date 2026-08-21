package com.act.hospitalmanagementsystem.tenant.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.tenant.entity.TenantEntity;
import com.act.hospitalmanagementsystem.tenant.service.SuperAdminTenantService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/super-admin/tenants")
public class SuperAdminTenantController {

    private final SuperAdminTenantService tenantService;

    public SuperAdminTenantController(SuperAdminTenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponseDTO<List<TenantEntity>>> getAllTenants() {
        List<TenantEntity> tenants = tenantService.getAllTenants();
        return ResponseEntity.ok(BaseResponseDTO.success("Tenants retrieved successfully", tenants));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponseDTO<TenantEntity>> createTenant(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        String subdomain = payload.get("subdomain");
        String adminEmail = payload.get("adminEmail");
        String plan = payload.get("plan");

        TenantEntity newTenant = tenantService.createTenant(name, subdomain, adminEmail, plan);
        return ResponseEntity.ok(BaseResponseDTO.success("Tenant provisioned successfully", newTenant));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponseDTO<TenantEntity>> toggleTenantStatus(@PathVariable String id) {
        TenantEntity updated = tenantService.toggleTenantStatus(id);
        return ResponseEntity.ok(BaseResponseDTO.success("Tenant status updated to " + updated.getStatus(), updated));
    }
}
