# Billing Module

## Overview

The Billing module manages all financial transactions within the Hospital Management System. It handles invoice generation, payment processing, insurance claims, and financial reporting. This module integrates with Appointment, Medical, Pharmacy, and Patient modules to create a comprehensive billing system.

---

## Features

### 1. Invoice Management
- **Invoice Generation** - Automatic invoice creation for services rendered
- **Service-Based Billing** - Support for consultations, procedures, medications, lab tests
- **Automatic Calculations** - Subtotal, tax, discount, and total calculations
- **Invoice Status Tracking** - Draft, pending, paid, overdue, cancelled
- **Line Item Management** - Detailed service descriptions with quantities and prices
- **Partial Payments** - Support for multiple partial payments on single invoice
- **Invoice Finalization** - Lock invoices after finalization to prevent modifications

### 2. Payment Processing
- **Multiple Payment Methods** - Cash, credit card, insurance, bank transfer
- **Payment Recording** - Record and track all payments
- **Refund Processing** - Handle refunds with approval workflow
- **Payment Receipts** - Generate payment receipts
- **Payment History** - Complete payment history tracking
- **Automatic Reminders** - Payment reminders for overdue invoices
- **Payment Reconciliation** - Match payments to invoices

### 3. Insurance Integration
- **Insurance Provider Management** - Manage insurance company information
- **Insurance Claim Submission** - Submit claims to insurance providers
- **Claim Status Tracking** - Track submitted, approved, rejected, pending claims
- **Co-pay & Deductible** - Automatic co-pay and deductible calculations
- **Eligibility Verification** - Verify patient insurance eligibility
- **EOB Processing** - Process Explanation of Benefits documents
- **Secondary Insurance** - Support for secondary insurance claims

### 4. Financial Reporting
- **Daily Revenue Reports** - Track daily revenue and collections
- **Monthly Billing Summaries** - Comprehensive monthly financial reports
- **Outstanding Balances** - Track unpaid invoices and balances
- **Payment Collection Reports** - Analyze payment collection trends
- **Insurance Claim Analytics** - Track insurance claim performance
- **Revenue by Service Type** - Breakdown of revenue by service category
- **Aging Reports** - Accounts receivable aging analysis

### 5. Patient Billing Portal
- **Invoice Viewing** - Patients can view their invoices online
- **Online Payments** - Secure online payment processing
- **Statement Downloads** - Download billing statements
- **Claim Status** - View insurance claim status
- **Payment Plans** - Manage payment plan arrangements

---

## Architecture

### Components

```
billing/
├── controller/
│   ├── InvoiceController.java           # Invoice management endpoints
│   ├── PaymentController.java           # Payment processing endpoints
│   ├── InsuranceProviderController.java # Insurance provider endpoints
│   ├── InsuranceClaimController.java    # Insurance claim endpoints
│   ├── RefundController.java            # Refund processing endpoints
│   └── BillingReportController.java     # Financial reporting endpoints
├── service/
│   ├── InvoiceService.java              # Invoice business logic
│   ├── PaymentService.java              # Payment processing logic
│   ├── InsuranceProviderService.java     # Insurance provider logic
│   ├── InsuranceClaimService.java        # Insurance claim logic
│   ├── RefundService.java               # Refund processing logic
│   ├── BillingReportService.java        # Financial reporting logic
│   └── BillingCalculationService.java   # Billing calculations
├── repository/
│   ├── InvoiceRepository.java           # Invoice data access
│   ├── InvoiceLineItemRepository.java  # Invoice line item data access
│   ├── PaymentRepository.java           # Payment data access
│   ├── InsuranceProviderRepository.java # Insurance provider data access
│   ├── InsuranceClaimRepository.java    # Insurance claim data access
│   └── RefundRepository.java            # Refund data access
├── entity/
│   ├── Invoice.java                     # Invoice entity
│   ├── InvoiceLineItem.java             # Invoice line item entity
│   ├── Payment.java                     # Payment entity
│   ├── InsuranceProvider.java           # Insurance provider entity
│   ├── InsuranceClaim.java              # Insurance claim entity
│   └── Refund.java                      # Refund entity
├── enums/
│   ├── InvoiceStatus.java               # Invoice status enum
│   ├── PaymentMethod.java               # Payment method enum
│   ├── PaymentStatus.java               # Payment status enum
│   ├── ClaimStatus.java                 # Insurance claim status enum
│   └── RefundStatus.java                # Refund status enum
├── dto/
│   ├── InvoiceDTO.java                  # Invoice response DTO
│   ├── CreateInvoiceRequest.java        # Create invoice request DTO
│   ├── UpdateInvoiceRequest.java        # Update invoice request DTO
│   ├── InvoiceLineItemDTO.java         # Invoice line item DTO
│   ├── PaymentDTO.java                  # Payment response DTO
│   ├── ProcessPaymentRequest.java      # Payment processing request DTO
│   ├── InsuranceProviderDTO.java       # Insurance provider DTO
│   ├── InsuranceClaimDTO.java           # Insurance claim DTO
│   ├── SubmitClaimRequest.java          # Claim submission request DTO
│   ├── RefundDTO.java                   # Refund response DTO
│   └── ProcessRefundRequest.java        # Refund processing request DTO
└── mapper/
    ├── InvoiceMapper.java               # Invoice entity/DTO mapper
    ├── PaymentMapper.java               # Payment entity/DTO mapper
    └── InsuranceClaimMapper.java        # Insurance claim entity/DTO mapper
```

### Data Model

#### Invoice Entity
- `id` (UUID) - Primary key
- `invoiceNumber` (String) - Unique invoice number
- `patientId` (UUID) - Associated patient
- `patientName` (String) - Patient name (denormalized)
- `invoiceDate` (LocalDate) - Invoice creation date
- `dueDate` (LocalDate) - Payment due date
- `status` (InvoiceStatus) - Current status
- `subtotal` (BigDecimal) - Subtotal before tax/discount
- `tax` (BigDecimal) - Tax amount
- `discount` (BigDecimal) - Discount amount
- `totalAmount` (BigDecimal) - Final total amount
- `paidAmount` (BigDecimal) - Amount paid so far
- `balanceAmount` (BigDecimal) - Remaining balance
- `paymentMethod` (PaymentMethod) - Primary payment method
- `insuranceClaimId` (UUID) - Associated insurance claim
- `notes` (String) - Additional notes
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### InvoiceLineItem Entity
- `id` (UUID) - Primary key
- `invoice` (Invoice) - Parent invoice
- `serviceType` (String) - Type of service (CONSULTATION, PROCEDURE, MEDICATION, LAB_TEST)
- `serviceId` (UUID) - Reference to service entity
- `description` (String) - Service description
- `quantity` (Integer) - Quantity of service
- `unitPrice` (BigDecimal) - Price per unit
- `lineTotal` (BigDecimal) - Line item total
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### Payment Entity
- `id` (UUID) - Primary key
- `invoice` (Invoice) - Associated invoice
- `patientId` (UUID) - Patient who made payment
- `paymentDate` (LocalDate) - Payment date
- `paymentMethod` (PaymentMethod) - Payment method used
- `amount` (BigDecimal) - Payment amount
- `referenceNumber` (String) - Transaction reference
- `status` (PaymentStatus) - Payment status
- `notes` (String) - Payment notes
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### InsuranceProvider Entity
- `id` (UUID) - Primary key
- `name` (String) - Provider name
- `contactPerson` (String) - Contact person
- `phone` (String) - Contact phone
- `email` (String) - Contact email
- `address` (String) - Provider address
- `claimSubmissionMethod` (String) - How to submit claims (API, EMAIL, PORTAL)
- `standardCoPay` (BigDecimal) - Standard co-pay amount
- `standardDeductible` (BigDecimal) - Standard deductible amount
- `isActive` (Boolean) - Provider active status
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### InsuranceClaim Entity
- `id` (UUID) - Primary key
- `invoice` (Invoice) - Associated invoice
- `provider` (InsuranceProvider) - Insurance provider
- `patientId` (UUID) - Patient
- `claimNumber` (String) - Insurance claim number
- `submissionDate` (LocalDate) - Date submitted to insurance
- `status` (ClaimStatus) - Current claim status
- `approvedAmount` (BigDecimal) - Amount approved by insurance
- `rejectionReason` (String) - Reason for rejection
- `notes` (String) - Additional notes
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### Refund Entity
- `id` (UUID) - Primary key
- `payment` (Payment) - Original payment
- `invoice` (Invoice) - Associated invoice
- `patientId` (UUID) - Patient
- `refundDate` (LocalDate) - Refund date
- `amount` (BigDecimal) - Refund amount
- `reason` (String) - Refund reason
- `status` (RefundStatus) - Refund status
- `processedBy` (String) - Who processed the refund
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

### Invoice Status States

| Status | Description |
|--------|-------------|
| DRAFT | Invoice is being created, not yet finalized |
| PENDING | Invoice finalized, awaiting payment |
| PAID | Invoice fully paid |
| PARTIALLY_PAID | Partial payment received |
| OVERDUE | Payment past due date |
| CANCELLED | Invoice cancelled |
| REFUNDED | Invoice refunded |

### Payment Method Types

| Method | Description |
|--------|-------------|
| CASH | Cash payment |
| CREDIT_CARD | Credit/debit card payment |
| INSURANCE | Insurance payment |
| BANK_TRANSFER | Bank transfer/wire |
| CHECK | Check payment |
| ONLINE | Online payment portal |

### Payment Status States

| Status | Description |
|--------|-------------|
| PENDING | Payment initiated but not confirmed |
| COMPLETED | Payment successfully processed |
| FAILED | Payment processing failed |
| REFUNDED | Payment refunded |
| CANCELLED | Payment cancelled |

### Claim Status States

| Status | Description |
|--------|-------------|
| DRAFT | Claim being prepared |
| SUBMITTED | Claim submitted to insurance |
| PENDING | Claim under review |
| APPROVED | Claim approved for payment |
| PARTIALLY_APPROVED | Claim partially approved |
| REJECTED | Claim rejected |
| PAID | Claim paid by insurance |

---

## API Endpoints

### Invoice Endpoints

#### Create Invoice
```http
POST /api/billing/invoices
Authorization: Bearer {accessToken}
Content-Type: application/json

{
  "patientId": "uuid",
  "invoiceDate": "2026-06-05",
  "dueDate": "2026-07-05",
  "lineItems": [
    {
      "serviceType": "CONSULTATION",
      "serviceId": "uuid",
      "description": "General Consultation",
      "quantity": 1,
      "unitPrice": 150.00
    }
  ],
  "paymentMethod": "CASH",
  "notes": "Regular consultation fee"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Invoice created successfully",
  "data": {
    "id": "uuid",
    "invoiceNumber": "INV-2026-0001",
    "patientId": "uuid",
    "patientName": "John Doe",
    "invoiceDate": "2026-06-05",
    "dueDate": "2026-07-05",
    "status": "DRAFT",
    "subtotal": 150.00,
    "tax": 0.00,
    "discount": 0.00,
    "totalAmount": 150.00,
    "paidAmount": 0.00,
    "balanceAmount": 150.00,
    "lineItems": [...]
  }
}
```

#### Get All Invoices (Paginated)
```http
GET /api/billing/invoices?page=0&size=5
Authorization: Bearer {accessToken}
```

#### Get Invoice by ID
```http
GET /api/billing/invoices/{id}
Authorization: Bearer {accessToken}
```

#### Get Patient Invoices
```http
GET /api/billing/invoices/patient/{patientId}
Authorization: Bearer {accessToken}
```

#### Finalize Invoice
```http
POST /api/billing/invoices/{id}/finalize
Authorization: Bearer {accessToken}
```

#### Update Invoice
```http
PUT /api/billing/invoices/{id}
Authorization: Bearer {accessToken}
Content-Type: application/json

{
  "dueDate": "2026-07-10",
  "notes": "Updated due date"
}
```

#### Delete Invoice
```http
DELETE /api/billing/invoices/{id}
Authorization: Bearer {accessToken}
```

### Payment Endpoints

#### Process Payment
```http
POST /api/billing/payments
Authorization: Bearer {accessToken}
Content-Type: application/json

{
  "invoiceId": "uuid",
  "paymentMethod": "CREDIT_CARD",
  "amount": 150.00,
  "referenceNumber": "TXN-123456",
  "notes": "Payment via credit card"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Payment processed successfully",
  "data": {
    "id": "uuid",
    "invoiceId": "uuid",
    "paymentDate": "2026-06-05",
    "paymentMethod": "CREDIT_CARD",
    "amount": 150.00,
    "referenceNumber": "TXN-123456",
    "status": "COMPLETED"
  }
}
```

#### Get All Payments (Paginated)
```http
GET /api/billing/payments?page=0&size=5
Authorization: Bearer {accessToken}
```

#### Get Invoice Payments
```http
GET /api/billing/payments/invoice/{invoiceId}
Authorization: Bearer {accessToken}
```

#### Process Refund
```http
POST /api/billing/payments/{paymentId}/refund
Authorization: Bearer {accessToken}
Content-Type: application/json

{
  "amount": 50.00,
  "reason": "Service cancelled"
}
```

### Insurance Endpoints

#### Create Insurance Provider
```http
POST /api/billing/insurance/providers
Authorization: Bearer {accessToken}
Content-Type: application/json

{
  "name": "Blue Cross",
  "contactPerson": "John Smith",
  "phone": "+1234567890",
  "email": "contact@bluecross.com",
  "address": "123 Main St",
  "claimSubmissionMethod": "API",
  "standardCoPay": 20.00,
  "standardDeductible": 500.00
}
```

#### Get All Insurance Providers
```http
GET /api/billing/insurance/providers
Authorization: Bearer {accessToken}
```

#### Submit Insurance Claim
```http
POST /api/billing/insurance/claims
Authorization: Bearer {accessToken}
Content-Type: application/json

{
  "invoiceId": "uuid",
  "providerId": "uuid",
  "patientId": "uuid"
}
```

#### Get All Claims (Paginated)
```http
GET /api/billing/insurance/claims?page=0&size=5
Authorization: Bearer {accessToken}
```

#### Update Claim Status
```http
PUT /api/billing/insurance/claims/{id}/status
Authorization: Bearer {accessToken}
Content-Type: application/json

{
  "status": "APPROVED",
  "approvedAmount": 130.00,
  "notes": "Claim approved"
}
```

### Reporting Endpoints

#### Daily Revenue Report
```http
GET /api/billing/reports/daily-revenue?date=2026-06-05
Authorization: Bearer {accessToken}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "date": "2026-06-05",
    "totalRevenue": 5430.00,
    "cashPayments": 1200.00,
    "cardPayments": 2800.00,
    "insurancePayments": 1430.00,
    "invoiceCount": 25,
    "paymentCount": 30
  }
}
```

#### Monthly Summary Report
```http
GET /api/billing/reports/monthly-summary?year=2026&month=6
Authorization: Bearer {accessToken}
```

#### Outstanding Balances Report
```http
GET /api/billing/reports/outstanding
Authorization: Bearer {accessToken}
```

#### Aging Report
```http
GET /api/billing/reports/aging
Authorization: Bearer {accessToken}
```

---

## Business Rules

### Invoice Finalization
- Invoices cannot be modified after finalization
- Finalized invoices trigger payment due date calculations
- Finalization requires at least one line item
- Finalized invoices are sent to patient notification system

### Payment Processing
- Partial payments are allowed
- Overpayments create credit balances for future invoices
- Refunds over $500 require manager approval
- Credit card payments require PCI DSS compliance
- Payment confirmation is sent to patient

### Insurance Claims
- Claims must be submitted within 30 days of service
- Secondary insurance claims are processed after primary
- Claim rejection requires manual review and documentation
- Claim approval updates invoice balance automatically
- EOB (Explanation of Benefits) must be stored for audit

### Discount Rules
- Senior citizens (65+) receive 10% discount
- Bulk payments (3+ months) receive 5% discount
- Staff members receive 20% discount
- Emergency services receive 5% discount
- Discounts cannot be combined (highest discount applies)

### Payment Due Dates
- Standard due date: 30 days from invoice date
- Insurance claims: 45 days from invoice date
- Emergency services: 15 days from invoice date
- Government programs: 60 days from invoice date

---

## Integration Points

### Appointment Module
- Generate consultation invoices after appointments
- Include appointment fees in billing
- Cancelled appointments generate credit notes

### Medical Module
- Generate invoices for procedures and treatments
- Include lab test charges in invoices
- Link medical records to invoice line items

### Pharmacy Module
- Include medication costs in patient invoices
- Track pharmacy payments separately
- Generate pharmacy-specific invoices

### Patient Module
- Link invoices to patient records
- Access patient billing history
- Manage patient insurance information
- Patient billing portal integration

### Notification Module
- Send invoice notifications to patients
- Payment reminder notifications
- Insurance claim status updates
- Refund confirmations

---

## Security Considerations

- Payment information must be encrypted at rest and in transit
- Complete audit trail for all financial transactions
- Role-based access control for billing operations
- PCI DSS compliance for credit card processing
- Regular security audits of billing data
- Sensitive financial data logging (redacted)
- Payment method verification and validation

---

## Performance Requirements

- Invoice generation: < 2 seconds
- Payment processing: < 3 seconds
- Report generation: < 10 seconds
- Support concurrent processing of 100+ invoices
- Database query optimization for large datasets
- Caching for frequently accessed financial reports
- Async processing for insurance claim submissions

---

## Testing Flow Scenarios

### Scenario 1: Create and Finalize Invoice

**Steps:**
1. Login as billing officer
2. Create invoice for patient consultation
3. Add line items (consultation fee, lab tests)
4. Finalize invoice
5. Verify invoice status changes to PENDING

### Scenario 2: Process Payment

**Steps:**
1. Login as billing officer
2. Get pending invoice
3. Process payment via credit card
4. Verify payment recorded
5. Verify invoice balance updated
6. Generate payment receipt

### Scenario 3: Submit Insurance Claim

**Steps:**
1. Create invoice with insurance payment method
2. Submit insurance claim
3. Track claim status
4. Process claim approval
5. Update invoice balance

### Scenario 4: Generate Financial Reports

**Steps:**
1. Login as admin or billing manager
2. Generate daily revenue report
3. Generate monthly summary
4. Generate aging report
5. Verify report accuracy

---

## Default Data

### Default Payment Methods
- CASH
- CREDIT_CARD
- INSURANCE
- BANK_TRANSFER
- CHECK
- ONLINE

### Default Invoice Statuses
- DRAFT
- PENDING
- PAID
- PARTIALLY_PAID
- OVERDUE
- CANCELLED
- REFUNDED

### Default Claim Statuses
- DRAFT
- SUBMITTED
- PENDING
- APPROVED
- PARTIALLY_APPROVED
- REJECTED
- PAID
