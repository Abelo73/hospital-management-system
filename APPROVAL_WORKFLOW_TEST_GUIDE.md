# Approval Workflow Test Guide

This guide provides a comprehensive step-by-step test case for the approval workflow feature from scratch.

## Prerequisites
- Application running on `http://localhost:8080`
- PostgreSQL database running
- `jq` installed for JSON parsing (optional but recommended)

---

## Step 1: Start the Application

```bash
cd /home/abel/Desktop/PROJECTS/2026-projects/hms/hospital-management-system
mvn spring-boot:run
```

Wait for the application to start. You should see:
```
Started HospitalManagementSystemApplication in X seconds
```

---

## Step 2: Login as Admin

The DataInitializer creates a default admin user.

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "Admin@123"
  }'
```

**Expected Response:**
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
  }
}
```

**Save the admin token:**
```bash
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

echo "Admin Token: $ADMIN_TOKEN"
```

---

## Step 3: Register a Doctor (Requires Document Verification)

Doctors require document verification before approval.

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "dr_john_smith",
    "password": "DoctorPass123",
    "email": "drsmith@hospital.com",
    "firstName": "John",
    "lastName": "Smith",
    "phoneNumber": "+1234567890",
    "roles": ["DOCTOR"]
  }'
```

**Expected Response:**
```json
{
  "success": true,
  "message": "Registration successful",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "userId": "uuid",
    "username": "dr_john_smith",
    "email": "drsmith@hospital.com",
    "firstName": "John",
    "lastName": "Smith",
    "roles": [],
    "permissions": []
  }
}
```

**Note:** The user is created but:
- `roles` is empty (will be assigned after approval)
- `permissions` is empty
- User has `PENDING_VERIFICATION` status

**Save the doctor token:**
```bash
DOCTOR_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "dr_john_smith", "password": "DoctorPass123"}' \
  | jq -r '.data.accessToken')

DOCTOR_USER_ID=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "dr_john_smith", "password": "DoctorPass123"}' \
  | jq -r '.data.userId')

echo "Doctor Token: $DOCTOR_TOKEN"
echo "Doctor User ID: $DOCTOR_USER_ID"
```

---

## Step 4: Check Doctor's Approval Status

```bash
curl -X GET http://localhost:8080/api/approvals/my-status \
  -H "Authorization: Bearer $DOCTOR_TOKEN"
```

**Expected Response:**
```json
{
  "success": true,
  "data": {
    "userId": "uuid",
    "username": "dr_john_smith",
    "email": "drsmith@hospital.com",
    "firstName": "John",
    "lastName": "Smith",
    "approvalStatus": "PENDING_VERIFICATION",
    "submittedAt": "2026-05-31T12:00:00",
    "approvedAt": null,
    "approvedBy": null,
    "rejectionReason": null,
    "requiresVerification": true,
    "pendingDocuments": 0,
    "verifiedDocuments": 0
  }
}
```

---

## Step 5: Doctor Uploads Medical License

Create a test PDF file first:
```bash
echo "Test Medical License" > /tmp/medical_license.pdf
```

Upload the document:
```bash
curl -X POST http://localhost:8080/api/documents/upload-my-document \
  -H "Authorization: Bearer $DOCTOR_TOKEN" \
  -F "file=@/tmp/medical_license.pdf" \
  -F 'metadata={
    "documentType": "MEDICAL_LICENSE",
    "documentName": "Medical License",
    "issueDate": "2020-01-01",
    "expiryDate": "2030-01-01",
    "issuingAuthority": "Medical Board",
    "documentNumber": "LIC-12345"
  }'
```

**Expected Response:**
```json
{
  "success": true,
  "message": "Document uploaded successfully",
  "data": {
    "id": "uuid",
    "userId": "uuid",
    "documentType": "MEDICAL_LICENSE",
    "documentStatus": "UPLOADED",
    "documentName": "Medical License",
    "filePath": "uploads/documents/uuid/filename.pdf",
    "fileSize": 1234,
    "fileType": "application/pdf",
    "issueDate": "2020-01-01",
    "expiryDate": "2030-01-01",
    "issuingAuthority": "Medical Board",
    "documentNumber": "LIC-12345",
    "verifiedAt": null,
    "verifiedBy": null,
    "verificationNotes": null,
    "rejectionReason": null
  }
}
```

**Save the document ID:**
```bash
DOCUMENT_ID=$(curl -X POST http://localhost:8080/api/documents/upload-my-document \
  -H "Authorization: Bearer $DOCTOR_TOKEN" \
  -F "file=@/tmp/medical_license.pdf" \
  -F 'metadata={
    "documentType": "MEDICAL_LICENSE",
    "documentName": "Medical License",
    "issueDate": "2020-01-01",
    "expiryDate": "2030-01-01",
    "issuingAuthority": "Medical Board",
    "documentNumber": "LIC-12345"
  }' \
  | jq -r '.data.id')

echo "Document ID: $DOCUMENT_ID"
```

---

## Step 6: Doctor Uploads Degree Certificate

Create a test PDF file:
```bash
echo "Test Degree Certificate" > /tmp/degree_certificate.pdf
```

Upload the document:
```bash
curl -X POST http://localhost:8080/api/documents/upload-my-document \
  -H "Authorization: Bearer $DOCTOR_TOKEN" \
  -F "file=@/tmp/degree_certificate.pdf" \
  -F 'metadata={
    "documentType": "DEGREE_CERTIFICATE",
    "documentName": "MBBS Degree Certificate",
    "issueDate": "2015-06-15",
    "expiryDate": null,
    "issuingAuthority": "Medical University",
    "documentNumber": "DEG-67890"
  }'
```

**Expected Response:** Similar to Step 5 with `documentType: "DEGREE_CERTIFICATE"`

---

## Step 7: Doctor Checks Their Documents

```bash
curl -X GET http://localhost:8080/api/documents/my-documents \
  -H "Authorization: Bearer $DOCTOR_TOKEN"
```

**Expected Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": "uuid",
      "documentType": "MEDICAL_LICENSE",
      "documentStatus": "UPLOADED",
      "documentName": "Medical License",
      ...
    },
    {
      "id": "uuid",
      "documentType": "DEGREE_CERTIFICATE",
      "documentStatus": "UPLOADED",
      "documentName": "MBBS Degree Certificate",
      ...
    }
  ]
}
```

---

## Step 8: Admin Views Pending Verification Documents

```bash
curl -X GET http://localhost:8080/api/documents/pending-verification \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

**Expected Response:** List of documents with `documentStatus: "UPLOADED"` or `"PENDING_VERIFICATION"`

---

## Step 9: Admin Verifies Medical License

```bash
curl -X POST http://localhost:8080/api/documents/verify/$DOCUMENT_ID \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "verified": true,
    "verificationNotes": "License verified with Medical Board. Valid until 2030."
  }'
```

**Expected Response:**
```json
{
  "success": true,
  "message": "Document verification processed",
  "data": {
    "id": "uuid",
    "documentStatus": "VERIFIED",
    "verifiedAt": "2026-05-31T12:30:00",
    "verifiedBy": "admin",
    "verificationNotes": "License verified with Medical Board. Valid until 2030.",
    ...
  }
}
```

---

## Step 10: Admin Verifies Degree Certificate

Get the degree certificate document ID first:
```bash
DEGREE_DOC_ID=$(curl -X GET http://localhost:8080/api/documents/my-documents \
  -H "Authorization: Bearer $DOCTOR_TOKEN" \
  | jq -r '.data[] | select(.documentType=="DEGREE_CERTIFICATE") | .id')

echo "Degree Document ID: $DEGREE_DOC_ID"
```

Verify the document:
```bash
curl -X POST http://localhost:8080/api/documents/verify/$DEGREE_DOC_ID \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "verified": true,
    "verificationNotes": "Degree certificate verified with Medical University."
  }'
```

---

## Step 11: Admin Views Pending Approvals

After all documents are verified, the approval request should move to PENDING_APPROVAL.

```bash
curl -X GET http://localhost:8080/api/approvals/pending \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

**Expected Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": "uuid",
      "userId": "uuid",
      "username": "dr_john_smith",
      "firstName": "John",
      "lastName": "Smith",
      "requestType": "ROLE_ASSIGNMENT",
      "requestedRole": "DOCTOR",
      "status": "PENDING_APPROVAL",
      "priority": 5,
      "submittedAt": "2026-05-31T12:00:00",
      "documentsRequired": true,
      "documentsVerified": true,
      ...
    }
  ]
}
```

**Save the approval request ID:**
```bash
APPROVAL_REQUEST_ID=$(curl -X GET http://localhost:8080/api/approvals/pending \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  | jq -r '.data[0].id')

echo "Approval Request ID: $APPROVAL_REQUEST_ID"
```

---

## Step 12: Admin Approves the Doctor

```bash
curl -X POST http://localhost:8080/api/approvals/process/$APPROVAL_REQUEST_ID \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "action": "APPROVE",
    "reason": "All documents verified and approved. Doctor credentials confirmed.",
    "notes": "Approved by HR Manager"
  }'
```

**Expected Response:**
```json
{
  "success": true,
  "message": "Approval request processed successfully",
  "data": {
    "id": "uuid",
    "status": "APPROVED",
    "approvedAt": "2026-05-31T13:00:00",
    "approvedBy": "admin",
    ...
  }
}
```

---

## Step 13: Doctor Logs In Again to Check Status

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "dr_john_smith",
    "password": "DoctorPass123"
  }'
```

**Expected Response:**
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "userId": "uuid",
    "username": "dr_john_smith",
    "email": "drsmith@hospital.com",
    "firstName": "John",
    "lastName": "Smith",
    "roles": ["DOCTOR"],
    "permissions": ["PATIENT_READ", "PATIENT_WRITE", "APPOINTMENT_READ", ...]
  }
}
```

**Note:** Now the user has:
- `roles: ["DOCTOR"]`
- Full doctor permissions

---

## Step 14: Doctor Checks Final Approval Status

```bash
curl -X GET http://localhost:8080/api/approvals/my-status \
  -H "Authorization: Bearer $DOCTOR_TOKEN"
```

**Expected Response:**
```json
{
  "success": true,
  "data": {
    "userId": "uuid",
    "username": "dr_john_smith",
    "email": "drsmith@hospital.com",
    "firstName": "John",
    "lastName": "Smith",
    "approvalStatus": "APPROVED",
    "submittedAt": "2026-05-31T12:00:00",
    "approvedAt": "2026-05-31T13:00:00",
    "approvedBy": "admin",
    "rejectionReason": null,
    "requiresVerification": true,
    "pendingDocuments": 0,
    "verifiedDocuments": 2
  }
}
```

---

## Step 15: Register a Receptionist (No Verification Required)

Receptionists don't require document verification.

```bash
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
```

**Expected Response:** Similar to Step 3 but with `approvalStatus: "PENDING_APPROVAL"` and `requiresVerification: false`

**Save the receptionist token and user ID:**
```bash
RECEPTIONIST_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "receptionist_jane", "password": "ReceptionPass123"}' \
  | jq -r '.data.accessToken')

RECEPTIONIST_USER_ID=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "receptionist_jane", "password": "ReceptionPass123"}' \
  | jq -r '.data.userId')

echo "Receptionist Token: $RECEPTIONIST_TOKEN"
echo "Receptionist User ID: $RECEPTIONIST_USER_ID"
```

---

## Step 16: Admin Approves Receptionist Directly

Get the approval request ID:
```bash
RECEPTIONIST_APPROVAL_ID=$(curl -X GET http://localhost:8080/api/approvals/pending \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  | jq -r '.data[] | select(.username=="receptionist_jane") | .id')

echo "Receptionist Approval ID: $RECEPTIONIST_APPROVAL_ID"
```

Approve the receptionist:
```bash
curl -X POST http://localhost:8080/api/approvals/process/$RECEPTIONIST_APPROVAL_ID \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "action": "APPROVE",
    "reason": "Receptionist role approved. No documents required.",
    "notes": "Approved by HR Manager"
  }'
```

---

## Step 17: Receptionist Logs In

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "receptionist_jane",
    "password": "ReceptionPass123"
  }'
```

**Expected Response:**
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "userId": "uuid",
    "username": "receptionist_jane",
    "email": "jane@hospital.com",
    "firstName": "Jane",
    "lastName": "Doe",
    "roles": ["RECEPTIONIST"],
    "permissions": ["PATIENT_READ", "PATIENT_WRITE", "APPOINTMENT_READ", ...]
  }
}
```

---

## Step 18: Test Document Rejection Scenario

Register another doctor for rejection test:

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "dr_rejected",
    "password": "DoctorPass123",
    "email": "drrejected@hospital.com",
    "firstName": "Rejected",
    "lastName": "Doctor",
    "phoneNumber": "+1234567890",
    "roles": ["DOCTOR"]
  }'
```

**Save tokens:**
```bash
REJECTED_DOCTOR_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "dr_rejected", "password": "DoctorPass123"}' \
  | jq -r '.data.accessToken')

REJECTED_DOCTOR_USER_ID=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "dr_rejected", "password": "DoctorPass123"}' \
  | jq -r '.data.userId')
```

Upload a document:
```bash
echo "Expired License" > /tmp/expired_license.pdf

REJECTED_DOC_ID=$(curl -X POST http://localhost:8080/api/documents/upload-my-document \
  -H "Authorization: Bearer $REJECTED_DOCTOR_TOKEN" \
  -F "file=@/tmp/expired_license.pdf" \
  -F 'metadata={
    "documentType": "MEDICAL_LICENSE",
    "documentName": "Expired Medical License",
    "issueDate": "2010-01-01",
    "expiryDate": "2020-01-01",
    "issuingAuthority": "Medical Board",
    "documentNumber": "LIC-99999"
  }' \
  | jq -r '.data.id')
```

Admin rejects the document:
```bash
curl -X POST http://localhost:8080/api/documents/verify/$REJECTED_DOC_ID \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "verified": false,
    "rejectionReason": "License expired on 2020-01-01. Please upload a valid license."
  }'
```

**Expected Response:**
```json
{
  "success": true,
  "message": "Document verification processed",
  "data": {
    "id": "uuid",
    "documentStatus": "REJECTED",
    "rejectionReason": "License expired on 2020-01-01. Please upload a valid license.",
    ...
  }
}
```

---

## Step 19: User Resubmits Corrected Document

User uploads a new valid document:
```bash
echo "Valid License" > /tmp/valid_license.pdf

NEW_DOC_ID=$(curl -X POST http://localhost:8080/api/documents/upload-my-document \
  -H "Authorization: Bearer $REJECTED_DOCTOR_TOKEN" \
  -F "file=@/tmp/valid_license.pdf" \
  -F 'metadata={
    "documentType": "MEDICAL_LICENSE",
    "documentName": "Valid Medical License",
    "issueDate": "2020-01-01",
    "expiryDate": "2030-01-01",
    "issuingAuthority": "Medical Board",
    "documentNumber": "LIC-88888"
  }' \
  | jq -r '.data.id')
```

Admin verifies the new document:
```bash
curl -X POST http://localhost:8080/api/documents/verify/$NEW_DOC_ID \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "verified": true,
    "verificationNotes": "New license is valid."
  }'
```

---

## Step 20: Admin Approves the Resubmitted Request

Get the approval request ID and approve:
```bash
RESUBMITTED_APPROVAL_ID=$(curl -X GET http://localhost:8080/api/approvals/pending \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  | jq -r '.data[] | select(.username=="dr_rejected") | .id')

curl -X POST http://localhost:8080/api/approvals/process/$RESUBMITTED_APPROVAL_ID \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "action": "APPROVE",
    "reason": "Corrected document verified and approved.",
    "notes": "Approved after resubmission"
  }'
```

---

## Step 21: Test Approval Rejection

Register another user for approval rejection test:

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "dr_denied",
    "password": "DoctorPass123",
    "email": "drdenied@hospital.com",
    "firstName": "Denied",
    "lastName": "Doctor",
    "phoneNumber": "+1234567890",
    "roles": ["DOCTOR"]
  }'
```

**Save tokens:**
```bash
DENIED_DOCTOR_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "dr_denied", "password": "DoctorPass123"}' \
  | jq -r '.data.accessToken')
```

Upload documents and verify them (skip to approval rejection):

Get approval request ID and reject:
```bash
DENIED_APPROVAL_ID=$(curl -X GET http://localhost:8080/api/approvals/pending \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  | jq -r '.data[] | select(.username=="dr_denied") | .id')

curl -X POST http://localhost:8080/api/approvals/process/$DENIED_APPROVAL_ID \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "action": "REJECT",
    "reason": "Background check failed. Unable to verify credentials.",
    "notes": "Please contact HR for more information"
  }'
```

**Expected Response:**
```json
{
  "success": true,
  "message": "Approval request processed successfully",
  "data": {
    "id": "uuid",
    "status": "REJECTED",
    "rejectionReason": "Background check failed. Unable to verify credentials.",
    ...
  }
}
```

---

## Step 22: Check Rejected User Status

```bash
curl -X GET http://localhost:8080/api/approvals/my-status \
  -H "Authorization: Bearer $DENIED_DOCTOR_TOKEN"
```

**Expected Response:**
```json
{
  "success": true,
  "data": {
    "userId": "uuid",
    "username": "dr_denied",
    "approvalStatus": "REJECTED",
    "rejectionReason": "Background check failed. Unable to verify credentials.",
    ...
  }
}
```

---

## Step 23: Test Resubmission After Rejection

```bash
curl -X POST http://localhost:8080/api/approvals/resubmit/$DENIED_DOCTOR_USER_ID \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

**Expected Response:**
```json
{
  "success": true,
  "message": "Approval request resubmitted successfully",
  "data": null
}
```

Check status again:
```bash
curl -X GET http://localhost:8080/api/approvals/my-status \
  -H "Authorization: Bearer $DENIED_DOCTOR_TOKEN"
```

**Expected Response:** Status should be `PENDING_VERIFICATION` again.

---

## Step 24: View All Approval Requests (Admin)

```bash
curl -X GET http://localhost:8080/api/approvals/all \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

**Expected Response:** List of all approval requests with various statuses.

---

## Step 25: View User's Approval History

```bash
curl -X GET http://localhost:8080/api/approvals/user/$DOCTOR_USER_ID \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

**Expected Response:** List of all approval requests for the specific user.

---

## Step 26: Test Access Control - Approved User Access

Doctor should be able to access doctor endpoints:

```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer $DOCTOR_TOKEN"
```

**Expected Response:** 200 OK with list of users (doctor has USER_READ permission).

---

## Step 27: Test Access Control - Pending User Limited Access

Pending user can only check status and upload documents:

```bash
# This should work
curl -X GET http://localhost:8080/api/approvals/my-status \
  -H "Authorization: Bearer $DENIED_DOCTOR_TOKEN"

# This should fail (no permissions)
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer $DENIED_DOCTOR_TOKEN"
```

**Expected Response:**
- First request: 200 OK
- Second request: 403 Forbidden

---

## Step 28: Cleanup (Optional)

Delete test users:

```bash
# Delete rejected doctor
curl -X DELETE http://localhost:8080/api/users/$REJECTED_DOCTOR_USER_ID \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# Delete denied doctor
curl -X DELETE http://localhost:8080/api/users/$DENIED_DOCTOR_USER_ID \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

---

## Summary of Approval Workflow States

1. **PENDING_SUBMISSION** - User registered, hasn't submitted documents
2. **PENDING_VERIFICATION** - Documents submitted, awaiting verification
3. **PENDING_APPROVAL** - Documents verified, awaiting final approval
4. **APPROVED** - User approved and account activated
5. **REJECTED** - User rejected, can resubmit
6. **SUSPENDED** - User account suspended

---

## Summary of Document Status States

1. **PENDING_UPLOAD** - Document not yet uploaded
2. **UPLOADED** - Document uploaded successfully
3. **PENDING_VERIFICATION** - Awaiting verification
4. **VERIFIED** - Document verified and approved
5. **REJECTED** - Document rejected
6. **EXPIRED** - Document expired

---

## Common Issues and Solutions

### Issue: "Document upload failed"
**Solution:** Ensure the file exists and the path is correct. Check file permissions.

### Issue: "Approval request not found"
**Solution:** Ensure the approval request ID is correct. Check that the user has a pending approval request.

### Issue: "Cannot approve request"
**Solution:** Ensure all required documents are verified before approving. Check `documentsVerified` field.

### Issue: "User cannot login"
**Solution:** Check if the user account is enabled. Pending users can login but have limited access.

---

## Testing Checklist

- [ ] Admin login successful
- [ ] Doctor registration creates user with PENDING_VERIFICATION status
- [ ] Doctor can upload documents
- [ ] Admin can view pending verification documents
- [ ] Admin can verify documents
- [ ] Admin can view pending approvals
- [ ] Admin can approve requests
- [ ] Approved user receives role and permissions
- [ ] Receptionist registration creates user with PENDING_APPROVAL status
- [ ] Receptionist can be approved without document verification
- [ ] Document rejection works correctly
- [ ] Document resubmission works correctly
- [ ] Approval rejection works correctly
- [ ] Approval resubmission works correctly
- [ ] Access control works correctly for approved users
- [ ] Access control works correctly for pending users
