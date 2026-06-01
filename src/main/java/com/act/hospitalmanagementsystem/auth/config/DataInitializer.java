package com.act.hospitalmanagementsystem.auth.config;

import com.act.hospitalmanagementsystem.auth.entity.Permission;
import com.act.hospitalmanagementsystem.auth.entity.Role;
import com.act.hospitalmanagementsystem.auth.entity.User;
import com.act.hospitalmanagementsystem.auth.repository.PermissionRepository;
import com.act.hospitalmanagementsystem.auth.repository.RoleRepository;
import com.act.hospitalmanagementsystem.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Initializing default data...");

        initializePermissions();
        initializeRoles();
        initializeAdminUser();
        initializeDoctorUser();
        initializeNurseUser();

        log.info("Default data initialization completed.");
    }

    private void initializePermissions() {
        log.info("Ensuring all default permissions exist...");
        
        String[] permissionNames = {
            // User permissions
            "USER_READ", "USER_WRITE", "USER_DELETE",
            // Role permissions
            "ROLE_READ", "ROLE_WRITE", "ROLE_DELETE",
            // Permission permissions
            "PERMISSION_READ", "PERMISSION_WRITE", "PERMISSION_DELETE",
            // Patient permissions
            "PATIENT_READ", "PATIENT_WRITE", "PATIENT_DELETE",
            // Medical Record permissions
            "MEDICAL_RECORD_READ", "MEDICAL_RECORD_WRITE", "MEDICAL_RECORD_DELETE",
            // Appointment permissions
            "APPOINTMENT_READ", "APPOINTMENT_WRITE", "APPOINTMENT_DELETE",
            // Doctor permissions
            "DOCTOR_READ", "DOCTOR_WRITE", "DOCTOR_DELETE",
            // Consultation permissions
            "CONSULTATION_READ", "CONSULTATION_WRITE", "CONSULTATION_DELETE",
            // Prescription permissions
            "PRESCRIPTION_READ", "PRESCRIPTION_WRITE", "PRESCRIPTION_DELETE",
            // Laboratory permissions
            "LABORATORY_READ", "LABORATORY_WRITE", "LABORATORY_DELETE",
            // Pharmacy permissions
            "PHARMACY_READ", "PHARMACY_WRITE", "PHARMACY_DELETE",
            // Billing permissions
            "BILLING_READ", "BILLING_WRITE", "BILLING_DELETE",
            // Inventory permissions
            "INVENTORY_READ", "INVENTORY_WRITE", "INVENTORY_DELETE",
            // HR permissions
            "HR_READ", "HR_WRITE", "HR_DELETE",
            // Nursing permissions
            "NURSING_READ", "NURSING_WRITE", "NURSING_DELETE",
            // Analytics permissions
            "ANALYTICS_READ",
            // Admin permissions
            "ADMIN_READ", "ADMIN_WRITE",
            // Approval permissions
            "APPROVAL_READ", "APPROVAL_WRITE"
        };

        int created = 0;
        for (String permissionName : permissionNames) {
            if (!permissionRepository.existsByName(permissionName)) {
                Permission permission = new Permission();
                permission.setName(permissionName);
                permission.setDescription("Permission to " + permissionName.replace("_", " ").toLowerCase());
                permissionRepository.save(permission);
                created++;
            }
        }
        
        if (created > 0) {
            log.info("Created {} new permissions.", created);
        } else {
            log.info("All permissions already exist.");
        }
    }

    private void initializeRoles() {
        // Always sync ADMIN role with all available permissions
        Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
            log.info("Creating ADMIN role...");
            Role r = new Role();
            r.setName("ADMIN");
            r.setDescription("Administrator with full access");
            return r;
        });
        adminRole.setPermissions(new HashSet<>(permissionRepository.findAll()));
        roleRepository.save(adminRole);
        log.info("ADMIN role synchronized with all permissions ({} total).", adminRole.getPermissions().size());

        // Always sync DOCTOR role with required permissions
        Role doctorRole = roleRepository.findByName("DOCTOR").orElseGet(() -> {
            log.info("Creating DOCTOR role...");
            Role r = new Role();
            r.setName("DOCTOR");
            r.setDescription("Doctor with medical access");
            return r;
        });
        doctorRole.setPermissions(getPermissionsForRole("DOCTOR"));
        roleRepository.save(doctorRole);
        log.info("DOCTOR role synchronized with permissions ({} total).", doctorRole.getPermissions().size());

        // Always sync NURSE role with required permissions
        Role nurseRole = roleRepository.findByName("NURSE").orElseGet(() -> {
            log.info("Creating NURSE role...");
            Role r = new Role();
            r.setName("NURSE");
            r.setDescription("Nurse with patient care access");
            return r;
        });
        nurseRole.setPermissions(getPermissionsForRole("NURSE"));
        roleRepository.save(nurseRole);
        log.info("NURSE role synchronized with permissions ({} total).", nurseRole.getPermissions().size());

        // Create other roles only if they don't exist
        if (roleRepository.count() <= 3) {
            log.info("Creating default non-medical roles...");
            // Create PHARMACIST role
            Role pharmacistRole = new Role();
            pharmacistRole.setName("PHARMACIST");
            pharmacistRole.setDescription("Pharmacist with pharmacy access");
            pharmacistRole.setPermissions(getPermissionsForRole("PHARMACIST"));
            roleRepository.save(pharmacistRole);

            // Create RECEPTIONIST role
            Role receptionistRole = new Role();
            receptionistRole.setName("RECEPTIONIST");
            receptionistRole.setDescription("Receptionist with front desk access");
            receptionistRole.setPermissions(getPermissionsForRole("RECEPTIONIST"));
            roleRepository.save(receptionistRole);

            // Create LAB_TECHNICIAN role
            Role labTechnicianRole = new Role();
            labTechnicianRole.setName("LAB_TECHNICIAN");
            labTechnicianRole.setDescription("Lab technician with laboratory access");
            labTechnicianRole.setPermissions(getPermissionsForRole("LAB_TECHNICIAN"));
            roleRepository.save(labTechnicianRole);

            // Create BILLING_OFFICER role
            Role billingOfficerRole = new Role();
            billingOfficerRole.setName("BILLING_OFFICER");
            billingOfficerRole.setDescription("Billing officer with finance access");
            billingOfficerRole.setPermissions(getPermissionsForRole("BILLING_OFFICER"));
            roleRepository.save(billingOfficerRole);

            // Create HR_MANAGER role
            Role hrManagerRole = new Role();
            hrManagerRole.setName("HR_MANAGER");
            hrManagerRole.setDescription("HR manager with HR access");
            hrManagerRole.setPermissions(getPermissionsForRole("HR_MANAGER"));
            roleRepository.save(hrManagerRole);

            log.info("Created 5 default non-medical roles.");
        } else {
            log.info("Non-medical roles already exist, skipping initialization.");
        }
    }

    private Set<Permission> getPermissionsForRole(String roleName) {
        Set<Permission> permissions = new HashSet<>();

        switch (roleName) {
            case "DOCTOR":
                permissions.addAll(permissionRepository.findAllByNameInAndDeletedFalse(Set.of(
                    "PATIENT_READ", "PATIENT_WRITE",
                    "MEDICAL_RECORD_READ", "MEDICAL_RECORD_WRITE",
                    "APPOINTMENT_READ", "APPOINTMENT_WRITE",
                    "CONSULTATION_READ", "CONSULTATION_WRITE",
                    "PRESCRIPTION_READ", "PRESCRIPTION_WRITE",
                    "LABORATORY_READ",
                    "PHARMACY_READ"
                )));
                break;

            case "NURSE":
                permissions.addAll(permissionRepository.findAllByNameInAndDeletedFalse(Set.of(
                    "PATIENT_READ", "PATIENT_WRITE",
                    "MEDICAL_RECORD_READ", "MEDICAL_RECORD_WRITE",
                    "APPOINTMENT_READ",
                    "CONSULTATION_READ", "CONSULTATION_WRITE",
                    "NURSING_READ", "NURSING_WRITE",
                    "LABORATORY_READ"
                )));
                break;
                
            case "PHARMACIST":
                permissions.addAll(permissionRepository.findAllByNameInAndDeletedFalse(Set.of(
                    "PATIENT_READ",
                    "PRESCRIPTION_READ", "PRESCRIPTION_WRITE",
                    "PHARMACY_READ", "PHARMACY_WRITE",
                    "INVENTORY_READ"
                )));
                break;
                
            case "RECEPTIONIST":
                permissions.addAll(permissionRepository.findAllByNameInAndDeletedFalse(Set.of(
                    "PATIENT_READ", "PATIENT_WRITE",
                    "APPOINTMENT_READ", "APPOINTMENT_WRITE",
                    "DOCTOR_READ"
                )));
                break;
                
            case "LAB_TECHNICIAN":
                permissions.addAll(permissionRepository.findAllByNameInAndDeletedFalse(Set.of(
                    "PATIENT_READ",
                    "LABORATORY_READ", "LABORATORY_WRITE",
                    "PRESCRIPTION_READ"
                )));
                break;
                
            case "BILLING_OFFICER":
                permissions.addAll(permissionRepository.findAllByNameInAndDeletedFalse(Set.of(
                    "PATIENT_READ",
                    "BILLING_READ", "BILLING_WRITE",
                    "APPOINTMENT_READ"
                )));
                break;
                
            case "HR_MANAGER":
                permissions.addAll(permissionRepository.findAllByNameInAndDeletedFalse(Set.of(
                    "USER_READ", "USER_WRITE",
                    "ROLE_READ",
                    "HR_READ", "HR_WRITE",
                    "DOCTOR_READ"
                )));
                break;
                
            default:
                break;
        }
        
        return permissions;
    }

    private void initializeAdminUser() {
        if (!userRepository.existsByUsername("admin")) {
            log.info("Creating default admin user...");

            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("ADMIN role not found"));

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setEmail("admin@hospital.com");
            admin.setFirstName("System");
            admin.setLastName("Administrator");
            admin.setEnabled(true);
            admin.setAccountNonExpired(true);
            admin.setAccountNonLocked(true);
            admin.setCredentialsNonExpired(true);
            admin.setApprovalStatus(com.act.hospitalmanagementsystem.auth.enums.ApprovalStatus.APPROVED);
            admin.setRoles(Set.of(adminRole));

            userRepository.save(admin);

            log.info("Default admin user created with username: admin");
        } else {
            log.info("Admin user already exists, skipping initialization.");
        }
    }

    private void initializeDoctorUser() {
        if (!userRepository.existsByUsername("doctor")) {
            log.info("Creating default doctor user...");

            Role doctorRole = roleRepository.findByName("DOCTOR")
                    .orElseThrow(() -> new RuntimeException("DOCTOR role not found"));

            User doctor = new User();
            doctor.setUsername("doctor");
            doctor.setPassword(passwordEncoder.encode("Doctor@123"));
            doctor.setEmail("doctor@hospital.com");
            doctor.setFirstName("John");
            doctor.setLastName("Smith");
            doctor.setPhoneNumber("+1234567890");
            doctor.setEnabled(true);
            doctor.setAccountNonExpired(true);
            doctor.setAccountNonLocked(true);
            doctor.setCredentialsNonExpired(true);
            doctor.setApprovalStatus(com.act.hospitalmanagementsystem.auth.enums.ApprovalStatus.APPROVED);
            doctor.setRoles(Set.of(doctorRole));

            userRepository.save(doctor);

            log.info("Default doctor user created with username: doctor");
        } else {
            log.info("Doctor user already exists, skipping initialization.");
        }
    }

    private void initializeNurseUser() {
        if (!userRepository.existsByUsername("nurse")) {
            log.info("Creating default nurse user...");

            Role nurseRole = roleRepository.findByName("NURSE")
                    .orElseThrow(() -> new RuntimeException("NURSE role not found"));

            User nurse = new User();
            nurse.setUsername("nurse");
            nurse.setPassword(passwordEncoder.encode("Nurse@123"));
            nurse.setEmail("nurse@hospital.com");
            nurse.setFirstName("Jane");
            nurse.setLastName("Doe");
            nurse.setPhoneNumber("+1234567891");
            nurse.setEnabled(true);
            nurse.setAccountNonExpired(true);
            nurse.setAccountNonLocked(true);
            nurse.setCredentialsNonExpired(true);
            nurse.setApprovalStatus(com.act.hospitalmanagementsystem.auth.enums.ApprovalStatus.APPROVED);
            nurse.setRoles(Set.of(nurseRole));

            userRepository.save(nurse);

            log.info("Default nurse user created with username: nurse");
        } else {
            log.info("Nurse user already exists, skipping initialization.");
        }
    }
}
