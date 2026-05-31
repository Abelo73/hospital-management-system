package com.act.hospitalmanagementsystem.auth.entity;

import com.act.hospitalmanagementsystem.auth.enums.ApprovalStatus;
import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "account_non_expired", nullable = false)
    private Boolean accountNonExpired = true;

    @Column(name = "account_non_locked", nullable = false)
    private Boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired", nullable = false)
    private Boolean credentialsNonExpired = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", nullable = false, length = 30)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING_SUBMISSION;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "approved_by", length = 100)
    private String approvedBy;

    @Column(name = "rejection_reason", length = 500)
    private String rejectionReason;

    @Column(name = "requires_verification", nullable = false)
    private Boolean requiresVerification = false;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        
        if (roles != null) {
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
                
                if (role.getPermissions() != null) {
                    for (com.act.hospitalmanagementsystem.auth.entity.Permission permission : role.getPermissions()) {
                        authorities.add(new SimpleGrantedAuthority(permission.getName()));
                    }
                }
            }
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        // Allow pending users to login with limited access
        // Only truly disabled users (e.g., suspended) cannot login
        return enabled || approvalStatus == ApprovalStatus.PENDING_SUBMISSION
                || approvalStatus == ApprovalStatus.PENDING_VERIFICATION
                || approvalStatus == ApprovalStatus.PENDING_APPROVAL;
    }

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }
}
