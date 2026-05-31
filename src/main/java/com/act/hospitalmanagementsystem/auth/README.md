# Authentication & Authorization Module

## Overview

The Auth module provides comprehensive authentication and authorization capabilities for the Hospital Management System. It implements JWT-based stateless authentication with Role-Based Access Control (RBAC) to secure all API endpoints.

---

## Features

### 1. Authentication
- **User Registration** - New user registration with automatic role assignment
- **User Login** - Username/password authentication with JWT token generation
- **Token Refresh** - Refresh token mechanism for extended sessions
- **Password Reset** - Admin-initiated password reset functionality
- **Account Status** - Enable/disable user accounts

### 2. Authorization
- **Role-Based Access Control (RBAC)** - Granular permission system
- **Method-Level Security** - `@PreAuthorize` annotations on controller methods
- **JWT Token Validation** - Automatic token validation on protected endpoints
- **Permission Management** - Dynamic permission assignment to roles

### 3. User Management
- **Create Users** - Admin can create new users with specific roles
- **Update Users** - Modify user details (name, email, phone, status)
- **Delete Users** - Soft delete with audit trail
- **View Users** - List all users with role information
- **Role Assignment** - Assign/remove roles from users

### 4. Role Management
- **Create Roles** - Define custom roles with descriptions
- **Update Roles** - Modify role details and permissions
- **Delete Roles** - Soft delete roles
- **View Roles** - List all roles with assigned permissions
- **Permission Assignment** - Assign/remove permissions from roles

### 5. Permission Management
- **Create Permissions** - Define custom permissions
- **Update Permissions** - Modify permission descriptions
- **Delete Permissions** - Soft delete permissions
- **View Permissions** - List all available permissions

### 6. Approval Workflow (Real-World Hospital Scenario)
- **User Registration with Approval** - New registrations require admin approval before activation
- **Document Verification** - Medical professionals must upload licenses/certificates for verification
- **Multi-Level Approval Process** - Document verification → HR review → Final approval
- **Role-Specific Requirements** - Doctors, nurses, pharmacists require document verification
- **Approval Status Tracking** - Track user approval status through the workflow
- **Document Management** - Upload, verify, and manage professional documents
- **Audit Trail** - Complete audit trail of all approval actions

---

## Architecture

### Components

```
auth/
├── config/
│   ├── SecurityConfig.java              # Spring Security configuration
│   ├── JwtAuthenticationFilter.java     # JWT token validation filter
│   ├── UserDetailsServiceImpl.java     # UserDetailsService implementation
│   ├── DataInitializer.java             # Default data seeding
│   └── JpaAuditingConfig.java          # JPA auditing configuration
├── controller/
│   ├── AuthController.java             # Authentication endpoints
│   ├── UserController.java              # User management endpoints
│   ├── RoleController.java              # Role management endpoints
│   ├── PermissionController.java        # Permission management endpoints
│   ├── ApprovalController.java         # Approval workflow endpoints
│   └── DocumentController.java         # Document management endpoints
├── service/
│   ├── AuthService.java                # Authentication business logic
│   ├── UserService.java                # User management business logic
│   ├── RoleService.java                # Role management business logic
│   ├── PermissionService.java          # Permission management business logic
│   ├── JwtService.java                 # JWT token generation/validation
│   ├── ApprovalService.java            # Approval workflow business logic
│   └── DocumentService.java            # Document management business logic
├── repository/
│   ├── UserRepository.java              # User data access
│   ├── RoleRepository.java              # Role data access
│   ├── PermissionRepository.java      # Permission data access
│   ├── ApprovalRequestRepository.java  # Approval request data access
│   └── UserDocumentRepository.java     # User document data access
├── entity/
│   ├── User.java                       # User entity (implements UserDetails)
│   ├── Role.java                       # Role entity
│   ├── Permission.java                 # Permission entity
│   ├── ApprovalRequest.java            # Approval request entity
│   └── UserDocument.java               # User document entity
├── enums/
│   ├── ApprovalStatus.java             # Approval status enum
│   ├── DocumentType.java               # Document type enum
│   └── DocumentStatus.java             # Document status enum
├── dto/
│   ├── LoginRequest.java               # Login request DTO
│   ├── LoginResponse.java              # Login response DTO
│   ├── RegisterRequest.java            # Registration request DTO
│   ├── UserDTO.java                    # User response DTO
│   ├── CreateUserRequest.java          # Create user request DTO
│   ├── UpdateUserRequest.java          # Update user request DTO
│   ├── ResetPasswordRequest.java       # Password reset request DTO
│   ├── RoleDTO.java                    # Role response DTO
│   ├── CreateRoleRequest.java          # Create role request DTO
│   ├── UpdateRoleRequest.java          # Update role request DTO
│   ├── PermissionDTO.java              # Permission response DTO
│   ├── CreatePermissionRequest.java    # Create permission request DTO
│   ├── UpdatePermissionRequest.java    # Update permission request DTO
│   ├── ApprovalRequestDTO.java         # Approval request DTO
│   ├── SubmitApprovalRequest.java      # Submit approval request DTO
│   ├── ApprovalActionRequest.java      # Approval action DTO
│   ├── UserApprovalStatusDTO.java      # User approval status DTO
│   ├── DocumentDTO.java                # Document DTO
│   ├── DocumentUploadRequest.java      # Document upload request DTO
│   └── DocumentVerificationRequest.java # Document verification request DTO
└── mapper/
    ├── UserMapper.java                 # User entity/DTO mapper
    ├── RoleMapper.java                 # Role entity/DTO mapper
    └── PermissionMapper.java           # Permission entity/DTO mapper
```

### Data Model

#### User Entity
- `id` (UUID) - Primary key
- `username` (String) - Unique username
- `password` (String) - Encrypted password (BCrypt)
- `email` (String) - Unique email address
- `firstName` (String) - User's first name
- `lastName` (String) - User's last name
- `phoneNumber` (String) - Contact number
- `enabled` (Boolean) - Account status
- `accountNonExpired` (Boolean) - Account expiration status
- `accountNonLocked` (Boolean) - Account lock status
- `credentialsNonExpired` (Boolean) - Password expiration status
- `approvalStatus` (ApprovalStatus) - Current approval status
- `submittedAt` (LocalDateTime) - When approval request was submitted
- `approvedAt` (LocalDateTime) - When approval was granted
- `approvedBy` (String) - Who approved the request
- `rejectionReason` (String) - Reason for rejection
- `requiresVerification` (Boolean) - Whether document verification is required
- `roles` (Set<Role>) - Assigned roles
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### ApprovalRequest Entity
- `id` (UUID) - Primary key
- `user` (User) - Associated user
- `requestType` (String) - Type of request (ROLE_ASSIGNMENT, ACCOUNT_ACTIVATION, etc.)
- `status` (ApprovalStatus) - Current status
- `requestedRole` (String) - Role being requested
- `priority` (Integer) - Priority level (1-10)
- `submittedAt` (LocalDateTime) - Submission timestamp
- `submittedBy` (String) - Who submitted
- `reviewedAt` (LocalDateTime) - Review timestamp
- `reviewedBy` (String) - Who reviewed
- `approvedAt` (LocalDateTime) - Approval timestamp
- `approvedBy` (String) - Who approved
- `rejectionReason` (String) - Rejection reason
- `notes` (String) - Additional notes
- `documentsRequired` (Boolean) - Whether documents are required
- `documentsVerified` (Boolean) - Whether documents are verified
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### UserDocument Entity
- `id` (UUID) - Primary key
- `user` (User) - Associated user
- `documentType` (DocumentType) - Type of document
- `documentStatus` (DocumentStatus) - Current status
- `documentName` (String) - Document name
- `filePath` (String) - File storage path
- `fileSize` (Long) - File size in bytes
- `fileType` (String) - MIME type
- `issueDate` (LocalDate) - Document issue date
- `expiryDate` (LocalDate) - Document expiry date
- `issuingAuthority` (String) - Issuing authority
- `documentNumber` (String) - Document number
- `verifiedAt` (LocalDateTime) - Verification timestamp
- `verifiedBy` (String) - Who verified
- `verificationNotes` (String) - Verification notes
- `rejectionReason` (String) - Rejection reason
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

### Approval Status States

| Status | Description |
|--------|-------------|
| PENDING_SUBMISSION | User registered but hasn't submitted required documents |
| PENDING_VERIFICATION | Documents submitted, awaiting verification |
| PENDING_APPROVAL | Documents verified, awaiting final approval |
| APPROVED | User approved and account activated |
| REJECTED | User rejected, can resubmit |
| SUSPENDED | User account suspended |

### Document Types

| Type | Description |
|------|-------------|
| MEDICAL_LICENSE | Medical practitioner license |
| NURSING_LICENSE | Nursing license |
| PHARMACY_LICENSE | Pharmacy license |
| DEGREE_CERTIFICATE | Educational degree certificate |
| IDENTITY_DOCUMENT | Government ID |
| BACKGROUND_CHECK | Background check report |
| CERTIFICATION | Professional certification |
| OTHER | Other documents |

### Document Status States

| Status | Description |
|--------|-------------|
| PENDING_UPLOAD | Document not yet uploaded |
| UPLOADED | Document uploaded successfully |
| PENDING_VERIFICATION | Awaiting verification |
| VERIFIED | Document verified and approved |
| REJECTED | Document rejected |
| EXPIRED | Document expired |

#### Role Entity
- `id` (UUID) - Primary key
- `name` (String) - Unique role name
- `description` (String) - Role description
- `permissions` (Set<Permission>) - Assigned permissions
- `users` (Set<User>) - Users with this role
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### Permission Entity
- `id` (UUID) - Primary key
- `name` (String) - Unique permission name
- `description` (String) - Permission description
- `roles` (Set<Role>) - Roles with this permission
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

---

## Default Data

### Default Permissions (44 total)

**User Management:**
- USER_READ, USER_WRITE, USER_DELETE

**Role Management:**
- ROLE_READ, ROLE_WRITE, ROLE_DELETE

**Permission Management:**
- PERMISSION_READ, PERMISSION_WRITE, PERMISSION_DELETE

**Patient Management:**
- PATIENT_READ, PATIENT_WRITE, PATIENT_DELETE

**Appointment Management:**
- APPOINTMENT_READ, APPOINTMENT_WRITE, APPOINTMENT_DELETE

**Doctor Management:**
- DOCTOR_READ, DOCTOR_WRITE, DOCTOR_DELETE

**Consultation Management:**
- CONSULTATION_READ, CONSULTATION_WRITE, CONSULTATION_DELETE

**Prescription Management:**
- PRESCRIPTION_READ, PRESCRIPTION_WRITE, PRESCRIPTION_DELETE

**Laboratory Management:**
- LABORATORY_READ, LABORATORY_WRITE, LABORATORY_DELETE

**Pharmacy Management:**
- PHARMACY_READ, PHARMACY_WRITE, PHARMACY_DELETE

**Billing Management:**
- BILLING_READ, BILLING_WRITE, BILLING_DELETE

**Inventory Management:**
- INVENTORY_READ, INVENTORY_WRITE, INVENTORY_DELETE

**HR Management:**
- HR_READ, HR_WRITE, HR_DELETE

**Nursing Management:**
- NURSING_READ, NURSING_WRITE, NURSING_DELETE

**Analytics:**
- ANALYTICS_READ

**Admin:**
- ADMIN_READ, ADMIN_WRITE

### Default Roles (8 total)

| Role | Description | Permissions |
|------|-------------|-------------|
| ADMIN | Administrator with full access | All 44 permissions |
| DOCTOR | Doctor with medical access | Patient, Appointment, Consultation, Prescription, Laboratory, Pharmacy (read/write) |
| NURSE | Nurse with patient care access | Patient, Appointment, Consultation, Nursing, Laboratory (read/write) |
| PHARMACIST | Pharmacist with pharmacy access | Patient, Prescription, Pharmacy, Inventory (read/write) |
| RECEPTIONIST | Receptionist with front desk access | Patient, Appointment, Doctor (read/write) |
| LAB_TECHNICIAN | Lab technician with laboratory access | Patient, Laboratory, Prescription (read/write) |
| BILLING_OFFICER | Billing officer with finance access | Patient, Billing, Appointment (read/write) |
| HR_MANAGER | HR manager with HR access | User, Role, HR, Doctor (read/write) |

### Default Admin User

- **Username:** admin
- **Password:** Admin@123
- **Email:** admin@hospital.com
- **Role:** ADMIN
- **Status:** Enabled

---

## API Endpoints

### Authentication Endpoints

#### Register User
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "password": "SecurePass123",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1234567890",
  "roles": ["DOCTOR"]
}
```

**Response:**
```json
{
  "success": true,
  "message": "Registration successful",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "userId": "uuid",
    "username": "john_doe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "roles": ["DOCTOR"],
    "permissions": ["PATIENT_READ", "PATIENT_WRITE", ...]
  },
  "timestamp": "2026-05-31T12:00:00"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "Admin@123"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "userId": "uuid",
    "username": "admin",
    "email": "admin@hospital.com",
    "firstName": "System",
    "lastName": "Administrator",
    "roles": ["ADMIN"],
    "permissions": ["USER_READ", "USER_WRITE", ...]
  },
  "timestamp": "2026-05-31T12:00:00"
}
```

#### Refresh Token
```http
POST /api/auth/refresh
Authorization: Bearer {refreshToken}
```

**Response:**
```json
{
  "success": true,
  "message": "Token refreshed successfully",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "userId": "uuid",
    "username": "admin",
    "email": "admin@hospital.com",
    "firstName": "System",
    "lastName": "Administrator",
    "roles": ["ADMIN"],
    "permissions": ["USER_READ", "USER_WRITE", ...]
  },
  "timestamp": "2026-05-31T12:00:00"
}
```

### User Management Endpoints

#### Get All Users
```http
GET /api/users
Authorization: Bearer {accessToken}
```

#### Get User by ID
```http
GET /api/users/{id}
Authorization: Bearer {accessToken}
```

#### Get User by Username
```http
GET /api/users/username/{username}
Authorization: Bearer {accessToken}
```

#### Create User
```http
POST /api/users
Authorization: Bearer {accessToken}
Content-Type: application/json

{
  "username": "jane_doe",
  "password": "SecurePass123",
  "email": "jane@example.com",
  "firstName": "Jane",
  "lastName": "Doe",
  "phoneNumber": "+1234567890",
  "roles": ["NURSE"]
}
```

#### Update User
```http
PUT /api/users/{id}
Authorization: Bearer {accessToken}
Content-Type: application/json

{
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane.smith@example.com",
  "enabled": true
}
```

#### Delete User
```http
DELETE /api/users/{id}
Authorization: Bearer {accessToken}
```

#### Reset Password
```http
POST /api/users/reset-password
Authorization: Bearer {accessToken}
Content-Type: application/json

{
  "username": "jane_doe",
  "newPassword": "NewSecurePass123"
}
```

#### Assign Role to User
```http
POST /api/users/{id}/roles/{roleName}
Authorization: Bearer {accessToken}
```

#### Remove Role from User
```http
DELETE /api/users/{id}/roles/{roleName}
Authorization: Bearer {accessToken}
```

### Role Management Endpoints

#### Get All Roles
```http
GET /api/roles
Authorization: Bearer {accessToken}
```

#### Get Role by ID
```http
GET /api/roles/{id}
Authorization: Bearer {accessToken}
```

#### Get Role by Name
```http
GET /api/roles/name/{name}
Authorization: Bearer {accessToken}
```

#### Create Role
```http
POST /api/roles
Authorization: Bearer {accessToken}
Content-Type: application/json

{
  "name": "CUSTOM_ROLE",
  "description": "Custom role description",
  "permissions": ["PATIENT_READ", "APPOINTMENT_READ"]
}
```

#### Update Role
```http
PUT /api/roles/{id}
Authorization: Bearer {accessToken}
Content-Type: application/json

{
  "description": "Updated description",
  "permissions": ["PATIENT_READ", "PATIENT_WRITE", "APPOINTMENT_READ"]
}
```

#### Delete Role
```http
DELETE /api/roles/{id}
Authorization: Bearer {accessToken}
```

#### Assign Permission to Role
```http
POST /api/roles/{id}/permissions/{permissionName}
Authorization: Bearer {accessToken}
```

#### Remove Permission from Role
```http
DELETE /api/roles/{id}/permissions/{permissionName}
Authorization: Bearer {accessToken}
```

### Permission Management Endpoints

#### Get All Permissions
```http
GET /api/permissions
Authorization: Bearer {accessToken}
```

#### Get Permission by ID
```http
GET /api/permissions/{id}
Authorization: Bearer {accessToken}
```

#### Get Permission by Name
```http
GET /api/permissions/name/{name}
Authorization: Bearer {accessToken}
```

#### Create Permission
```http
POST /api/permissions
Authorization: Bearer {accessToken}
Content-Type: application/json

{
  "name": "CUSTOM_PERMISSION",
  "description": "Custom permission description"
}
```

#### Update Permission
```http
PUT /api/permissions/{id}
Authorization: Bearer {accessToken}
Content-Type: application/json

{
  "description": "Updated description"
}
```

#### Delete Permission
```http
DELETE /api/permissions/{id}
Authorization: Bearer {accessToken}
```

### Approval Workflow Endpoints

#### Get My Approval Status
```http
GET /api/approvals/my-status
Authorization: Bearer {accessToken}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "userId": "uuid",
    "username": "john_doe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "approvalStatus": "PENDING_VERIFICATION",
    "submittedAt": "2026-05-31T12:00:00",
    "approvedAt": null,
    "approvedBy": null,
    "rejectionReason": null,
    "requiresVerification": true,
    "pendingDocuments": 2,
    "verifiedDocuments": 1
  }
}
```

#### Get Pending Approvals (Admin)
```http
GET /api/approvals/pending
Authorization: Bearer {adminToken}
```

#### Submit Approval Request (Admin)
```http
POST /api/approvals/submit/{userId}
Authorization: Bearer {adminToken}
Content-Type: application/json

{
  "requestType": "ROLE_ASSIGNMENT",
  "requestedRole": "DOCTOR",
  "priority": 5,
  "notes": "Requesting doctor role assignment"
}
```

#### Process Approval Request (Admin)
```http
POST /api/approvals/process/{requestId}
Authorization: Bearer {adminToken}
Content-Type: application/json

{
  "action": "APPROVE",
  "reason": "All documents verified and approved",
  "notes": "Approved by HR manager"
}
```

#### Resubmit Approval Request
```http
POST /api/approvals/resubmit/{userId}
Authorization: Bearer {accessToken}
```

### Document Management Endpoints

#### Get My Documents
```http
GET /api/documents/my-documents
Authorization: Bearer {accessToken}
```

#### Upload Document
```http
POST /api/documents/upload-my-document
Authorization: Bearer {accessToken}
Content-Type: multipart/form-data

file: [binary file]
metadata: {
  "documentType": "MEDICAL_LICENSE",
  "documentName": "Medical License",
  "issueDate": "2020-01-01",
  "expiryDate": "2030-01-01",
  "issuingAuthority": "Medical Board",
  "documentNumber": "LIC-12345"
}
```

#### Get Pending Verification Documents (Admin)
```http
GET /api/documents/pending-verification
Authorization: Bearer {adminToken}
```

#### Verify Document (Admin)
```http
POST /api/documents/verify/{documentId}
Authorization: Bearer {adminToken}
Content-Type: application/json

{
  "verified": true,
  "verificationNotes": "Document verified and authentic"
}
```

#### Delete Document
```http
DELETE /api/documents/{documentId}
Authorization: Bearer {accessToken}
```

---

## Testing Flow Scenarios

### Scenario 1: Admin User Registration and Login

**Steps:**
1. Start the application (DataInitializer creates default admin user)
2. Login as admin user
3. Verify JWT tokens are returned
4. Use access token to access protected endpoints

**Test Commands:**
```bash
# Login as admin
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "Admin@123"
  }'

# Expected: 200 OK with access and refresh tokens
```

---

### Scenario 2: Register New Doctor User

**Steps:**
1. Login as admin to get access token
2. Register a new doctor user
3. Verify user is created with DOCTOR role
4. Login as the new doctor
5. Verify doctor has appropriate permissions

**Test Commands:**
```bash
# Step 1: Login as admin
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

# Step 2: Register new doctor
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "dr_smith",
    "password": "DoctorPass123",
    "email": "smith@hospital.com",
    "firstName": "John",
    "lastName": "Smith",
    "phoneNumber": "+1234567890",
    "roles": ["DOCTOR"]
  }'

# Expected: 200 OK with tokens for new doctor
```

---

### Scenario 3: Create User via Admin API

**Steps:**
1. Login as admin
2. Create a new nurse user using User API
3. Verify user is created
4. Assign additional roles if needed
5. Enable/disable user account

**Test Commands:**
```bash
# Login as admin
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

# Create nurse user
curl -X POST http://localhost:8080/api/users \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "nurse_jane",
    "password": "NursePass123",
    "email": "jane@hospital.com",
    "firstName": "Jane",
    "lastName": "Doe",
    "phoneNumber": "+1234567890",
    "roles": ["NURSE"]
  }'

# Expected: 200 OK with user details
```

---

### Scenario 4: Role and Permission Management

**Steps:**
1. Login as admin
2. Create a new custom role
3. Assign permissions to the role
4. Create a new user with custom role
5. Verify user has correct permissions

**Test Commands:**
```bash
# Login as admin
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

# Create custom role
curl -X POST http://localhost:8080/api/roles \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "SUPERVISOR",
    "description": "Supervisor role with limited access",
    "permissions": ["PATIENT_READ", "APPOINTMENT_READ", "DOCTOR_READ"]
  }'

# Expected: 200 OK with role details
```

---

### Scenario 5: Token Refresh Flow

**Steps:**
1. Login to get access and refresh tokens
2. Wait for access token to expire (or simulate)
3. Use refresh token to get new access token
4. Continue using new access token

**Test Commands:**
```bash
# Login
RESPONSE=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}')

ACCESS_TOKEN=$(echo $RESPONSE | jq -r '.data.accessToken')
REFRESH_TOKEN=$(echo $RESPONSE | jq -r '.data.refreshToken')

# Refresh token
curl -X POST http://localhost:8080/api/auth/refresh \
  -H "Authorization: Bearer $REFRESH_TOKEN"

# Expected: 200 OK with new access and refresh tokens
```

---

### Scenario 6: Access Control - Unauthorized Access

**Steps:**
1. Try to access protected endpoint without token
2. Verify 401 Unauthorized response
3. Try to access endpoint with invalid token
4. Verify 401 Unauthorized response

**Test Commands:**
```bash
# Access without token
curl -X GET http://localhost:8080/api/users

# Expected: 401 Unauthorized

# Access with invalid token
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer invalid_token"

# Expected: 401 Unauthorized
```

---

### Scenario 7: Access Control - Forbidden Access

**Steps:**
1. Login as receptionist (limited permissions)
2. Try to access admin-only endpoint
3. Verify 403 Forbidden response
4. Try to access endpoint requiring different permission

**Test Commands:**
```bash
# Login as receptionist
RECEPTIONIST_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "receptionist", "password": "password"}' \
  | jq -r '.data.accessToken')

# Try to delete user (requires USER_DELETE permission)
curl -X DELETE http://localhost:8080/api/users/{id} \
  -H "Authorization: Bearer $RECEPTIONIST_TOKEN"

# Expected: 403 Forbidden
```

---

### Scenario 8: Password Reset

**Steps:**
1. Login as admin
2. Reset password for a user
3. User can login with new password
4. Old password no longer works

**Test Commands:**
```bash
# Login as admin
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

# Reset password
curl -X POST http://localhost:8080/api/users/reset-password \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "nurse_jane",
    "newPassword": "NewPassword123"
  }'

# Expected: 200 OK
```

---

### Scenario 9: Soft Delete and Audit

**Steps:**
1. Create a user
2. Delete the user (soft delete)
3. Verify user is marked as deleted
4. Try to access deleted user
5. Verify audit fields are populated

**Test Commands:**
```bash
# Login as admin
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

# Create user
USER_RESPONSE=$(curl -X POST http://localhost:8080/api/users \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test_user",
    "password": "TestPass123",
    "email": "test@example.com",
    "firstName": "Test",
    "lastName": "User",
    "roles": ["RECEPTIONIST"]
  }')

USER_ID=$(echo $USER_RESPONSE | jq -r '.data.id')

# Delete user
curl -X DELETE http://localhost:8080/api/users/$USER_ID \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# Expected: 200 OK, user marked as deleted
```

---

### Scenario 10: Role Assignment and Removal

**Steps:**
1. Create a user with base role
2. Assign additional role to user
3. Verify user has both roles
4. Remove one role
5. Verify user has remaining role

**Test Commands:**
```bash
# Login as admin
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

# Create user
USER_RESPONSE=$(curl -X POST http://localhost:8080/api/users \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "multi_role_user",
    "password": "MultiPass123",
    "email": "multi@example.com",
    "firstName": "Multi",
    "lastName": "Role",
    "roles": ["RECEPTIONIST"]
  }')

USER_ID=$(echo $USER_RESPONSE | jq -r '.data.id')

# Assign additional role
curl -X POST http://localhost:8080/api/users/$USER_ID/roles/BILLING_OFFICER \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# Expected: 200 OK

# Remove role
curl -X DELETE http://localhost:8080/api/users/$USER_ID/roles/BILLING_OFFICER \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# Expected: 200 OK
```

---

### Scenario 11: Doctor Registration with Approval Workflow

**Steps:**
1. Register as a doctor (requires document verification)
2. User is created with PENDING_VERIFICATION status
3. User can login but has no permissions
4. User uploads medical license
5. Admin verifies document
6. Admin approves user
7. User gets DOCTOR role and permissions

**Test Commands:**
```bash
# Step 1: Register as doctor
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "dr_john",
    "password": "DoctorPass123",
    "email": "drjohn@hospital.com",
    "firstName": "John",
    "lastName": "Smith",
    "phoneNumber": "+1234567890",
    "roles": ["DOCTOR"]
  }'

# Expected: 200 OK with tokens, but user has PENDING_VERIFICATION status

# Step 2: Login to check approval status
DOCTOR_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "dr_john", "password": "DoctorPass123"}' \
  | jq -r '.data.accessToken')

curl -X GET http://localhost:8080/api/approvals/my-status \
  -H "Authorization: Bearer $DOCTOR_TOKEN"

# Expected: 200 OK with approvalStatus: PENDING_VERIFICATION

# Step 3: Upload medical license (requires file upload)
# This would be done via multipart/form-data

# Step 4: Admin verifies document
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

curl -X POST http://localhost:8080/api/documents/verify/{documentId} \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"verified": true, "verificationNotes": "License verified"}'

# Step 5: Admin approves user
curl -X POST http://localhost:8080/api/approvals/process/{requestId} \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"action": "APPROVE", "reason": "All documents verified"}'

# Expected: 200 OK, user now has DOCTOR role and permissions
```

---

### Scenario 12: Receptionist Registration (No Verification Required)

**Steps:**
1. Register as a receptionist (no document verification required)
2. User is created with PENDING_APPROVAL status
3. Admin approves user directly
4. User gets RECEPTIONIST role and permissions

**Test Commands:**
```bash
# Step 1: Register as receptionist
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "receptionist_jane",
    "password": "ReceptionPass123",
    "email": "jane@hospital.com",
    "firstName": "Jane",
    "lastName": "Doe",
    "phoneNumber": "+1234567890",
    "roles": ["RECEPTIONIST"]
  }'

# Expected: 200 OK with tokens, user has PENDING_APPROVAL status

# Step 2: Admin approves user
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

curl -X POST http://localhost:8080/api/approvals/process/{requestId} \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"action": "APPROVE", "reason": "Approved for receptionist role"}'

# Expected: 200 OK, user now has RECEPTIONIST role and permissions
```

---

### Scenario 13: Document Rejection and Resubmission

**Steps:**
1. User uploads document
2. Admin rejects document with reason
3. User receives rejection reason
4. User uploads corrected document
5. Admin verifies and approves

**Test Commands:**
```bash
# Step 1: Admin rejects document
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

curl -X POST http://localhost:8080/api/documents/verify/{documentId} \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"verified": false, "rejectionReason": "License expired"}'

# Expected: 200 OK, document marked as REJECTED

# Step 2: User uploads corrected document
DOCTOR_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "dr_john", "password": "DoctorPass123"}' \
  | jq -r '.data.accessToken')

# Upload new document via multipart/form-data

# Step 3: Admin verifies new document
curl -X POST http://localhost:8080/api/documents/verify/{newDocumentId} \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"verified": true, "verificationNotes": "New license valid"}'

# Expected: 200 OK, document marked as VERIFIED
```

---

## Security Features

### JWT Token Configuration
- **Access Token Expiration:** 1 hour (3600000 ms)
- **Refresh Token Expiration:** 7 days (604800000 ms)
- **Signing Algorithm:** HMAC-SHA
- **Token Type:** Bearer

### Password Security
- **Encryption:** BCrypt with default strength (10 rounds)
- **Minimum Length:** 6 characters
- **No plain text storage**

### Account Security
- **Account Locking:** Support for locked accounts
- **Account Expiration:** Support for expired accounts
- **Credential Expiration:** Support for password expiration
- **Account Status:** Enable/disable functionality

### Audit Trail
- **Created By:** Tracks who created the record
- **Updated By:** Tracks who last modified the record
- **Timestamps:** Created and updated timestamps
- **Soft Delete:** Records marked as deleted, not physically removed
- **Version:** Optimistic locking with version field

---

## Configuration

### Application Properties

```yaml
jwt:
  secret: veryLongSecretKeyForHospitalManagementSystem2026VerySecure
  access-token-expiration: 3600000    # 1 hour
  refresh-token-expiration: 604800000 # 7 days

app:
  admin:
    email: admin@hospital.com
    default-password: Admin@123
```

### Security Configuration

- **Stateless Session Management:** No server-side session storage
- **CSRF Protection:** Disabled for API
- **CORS:** Configured for frontend origins
- **Public Endpoints:** `/api/auth/**`, `/swagger-ui/**`, `/v3/api-docs/**`, `/actuator/**`
- **Protected Endpoints:** All other endpoints require authentication

---

## Error Handling

### Error Response Format

```json
{
  "success": false,
  "message": "Error description",
  "errorCode": "ERROR_CODE",
  "timestamp": "2026-05-31T12:00:00"
}
```

### Common Error Codes

| Error Code | Description | HTTP Status |
|------------|-------------|-------------|
| RESOURCE_NOT_FOUND | Requested resource not found | 404 |
| BAD_REQUEST | Invalid request data | 400 |
| UNAUTHORIZED | Authentication required or failed | 401 |
| FORBIDDEN | Insufficient permissions | 403 |
| USERNAME_EXISTS | Username already taken | 400 |
| EMAIL_EXISTS | Email already registered | 400 |
| ROLE_EXISTS | Role name already exists | 400 |
| PERMISSION_EXISTS | Permission name already exists | 400 |
| INVALID_CREDENTIALS | Invalid username or password | 401 |
| ACCOUNT_DISABLED | User account is disabled | 401 |
| VALIDATION_ERROR | Request validation failed | 400 |
| INTERNAL_SERVER_ERROR | Unexpected server error | 500 |

---

## Best Practices

### For Developers
1. **Always use constructor injection** for dependencies
2. **Use DTOs for all API requests/responses** - never expose entities
3. **Implement proper validation** on all request DTOs
4. **Use @PreAuthorize annotations** for method-level security
5. **Log important security events** (login, failed attempts, etc.)
6. **Never hardcode secrets** - use environment variables
7. **Implement rate limiting** for authentication endpoints
8. **Use HTTPS in production** for all communications

### For Administrators
1. **Change default admin password** immediately after first login
2. **Create separate admin accounts** for different administrators
3. **Regularly review user access** and remove unnecessary permissions
4. **Enable account lockout** after failed login attempts
5. **Implement password policies** (complexity, expiration)
6. **Monitor audit logs** for suspicious activity
7. **Keep JWT secret secure** and rotate periodically
8. **Use role-based access** rather than direct permission assignment

---

## Future Enhancements

- **Two-Factor Authentication (2FA)**
- **OAuth2/OIDC integration** (Google, Microsoft, etc.)
- **Password strength validation** with custom policies
- **Account lockout after failed attempts**
- **Email verification** during registration
- **Password reset via email** (self-service)
- **Session management** (view active sessions, revoke)
- **IP-based access control**
- **Time-based access restrictions**
- **Audit log viewer** in admin panel
- **Permission inheritance** (role hierarchy)
- **Dynamic permissions** (created at runtime)
- **SAML integration** for enterprise SSO
