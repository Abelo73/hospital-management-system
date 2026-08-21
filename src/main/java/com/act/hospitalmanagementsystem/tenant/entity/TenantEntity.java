package com.act.hospitalmanagementsystem.tenant.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tenants")
public class TenantEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String subdomain;

    @Column(name = "admin_email", nullable = false)
    private String adminEmail;

    @Column(nullable = false)
    private String plan; // FREE, PRO, ULTRA

    @Column(nullable = false)
    private String status; // ACTIVE, SUSPENDED

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "users_count")
    private Integer usersCount = 1;

    @Column(name = "patients_count")
    private Integer patientsCount = 0;

    public TenantEntity() {
    }

    public TenantEntity(String id, String name, String subdomain, String adminEmail, String plan, String status) {
        this.id = id;
        this.name = name;
        this.subdomain = subdomain;
        this.adminEmail = adminEmail;
        this.plan = plan;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubdomain() {
        return subdomain;
    }

    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public Integer getPatientsCount() {
        return patientsCount;
    }

    public void setPatientsCount(Integer patientsCount) {
        this.patientsCount = patientsCount;
    }
}
