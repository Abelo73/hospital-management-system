# Billing Module Specification

## Overview
The Billing Module manages all financial transactions within the hospital management system, including invoice generation, payment processing, insurance claims, and financial reporting.

## Core Features

### 1. Invoice Management
- Generate invoices for services rendered
- Support for multiple billing types (consultation, procedures, medications, lab tests)
- Automatic invoice calculation based on service rates
- Invoice status tracking (draft, pending, paid, overdue, cancelled)
- Invoice line items with detailed service descriptions
- Tax and discount calculations
- Partial payment support

### 2. Payment Processing
- Multiple payment methods (cash, credit card, insurance, bank transfer)
- Payment recording and reconciliation
- Refund processing
- Payment receipt generation
- Payment history tracking
- Automatic payment reminders for overdue invoices

### 3. Insurance Integration
- Insurance provider management
- Insurance claim submission
- Claim status tracking (submitted, approved, rejected, pending)
- Co-pay and deductible calculations
- Insurance eligibility verification
- Explanation of Benefits (EOB) processing

### 4. Financial Reporting
- Daily revenue reports
- Monthly billing summaries
- Outstanding balances report
- Payment collection reports
- Insurance claim analytics
- Revenue by service type
- Aging reports for accounts receivable

### 5. Patient Billing Portal
- View invoices and payment history
- Make online payments
- Download statements
- View insurance claim status
- Payment plan management

## Entity Model

### Invoice
- id, invoiceNumber, patientId, patientName
- invoiceDate, dueDate, status
- subtotal, tax, discount, totalAmount
- paidAmount, balanceAmount
- paymentMethod, insuranceClaimId
- notes, createdAt, updatedAt

### InvoiceLineItem
- id, invoiceId, serviceType, serviceId
- description, quantity, unitPrice
- lineTotal, createdAt

### Payment
- id, invoiceId, patientId
- paymentDate, paymentMethod
- amount, referenceNumber
- status, notes, createdAt

### InsuranceProvider
- id, name, contactPerson, phone, email
- address, claimSubmissionMethod
- standardCoPay, standardDeductible
- isActive, createdAt

### InsuranceClaim
- id, invoiceId, providerId, patientId
- claimNumber, submissionDate
- status, approvedAmount
- rejectionReason, notes, createdAt

### Refund
- id, paymentId, invoiceId, patientId
- refundDate, amount, reason
- status, processedBy, createdAt

## API Endpoints

### Invoice Endpoints
- POST /api/billing/invoices - Create invoice
- GET /api/billing/invoices - List invoices (paginated)
- GET /api/billing/invoices/{id} - Get invoice details
- PUT /api/billing/invoices/{id} - Update invoice
- DELETE /api/billing/invoices/{id} - Delete invoice
- POST /api/billing/invoices/{id}/finalize - Finalize invoice
- GET /api/billing/invoices/patient/{patientId} - Get patient invoices

### Payment Endpoints
- POST /api/billing/payments - Process payment
- GET /api/billing/payments - List payments (paginated)
- GET /api/billing/payments/{id} - Get payment details
- POST /api/billing/payments/{id}/refund - Process refund
- GET /api/billing/payments/invoice/{invoiceId} - Get invoice payments

### Insurance Endpoints
- POST /api/billing/insurance/providers - Create provider
- GET /api/billing/insurance/providers - List providers
- POST /api/billing/insurance/claims - Submit claim
- GET /api/billing/insurance/claims - List claims
- PUT /api/billing/insurance/claims/{id}/status - Update claim status

### Reporting Endpoints
- GET /api/billing/reports/daily-revenue - Daily revenue report
- GET /api/billing/reports/monthly-summary - Monthly summary
- GET /api/billing/reports/outstanding - Outstanding balances
- GET /api/billing/reports/aging - Aging report

## Integration Points

### Appointment Module
- Generate consultation invoices after appointments
- Include appointment fees in billing

### Medical Module
- Generate invoices for procedures and treatments
- Include lab test charges in invoices

### Pharmacy Module
- Include medication costs in patient invoices
- Track pharmacy payments separately

### Patient Module
- Link invoices to patient records
- Access patient billing history
- Insurance information management

## Business Rules

1. **Invoice Finalization**
   - Invoices cannot be modified after finalization
   - Finalized invoices trigger payment due date calculations

2. **Payment Processing**
   - Partial payments are allowed
   - Overpayments create credit balances
   - Refunds require approval for amounts over $500

3. **Insurance Claims**
   - Claims must be submitted within 30 days of service
   - Secondary insurance claims are processed after primary
   - Claim rejection requires manual review

4. **Discount Rules**
   - Senior citizens receive 10% discount
   - Bulk payments (3+ months) receive 5% discount
   - Staff members receive 20% discount

## Security Considerations

- Payment information must be encrypted
- Audit trail for all financial transactions
- Role-based access control for billing operations
- PCI DSS compliance for credit card processing
- Regular security audits of billing data

## Performance Requirements

- Invoice generation: < 2 seconds
- Payment processing: < 3 seconds
- Report generation: < 10 seconds
- Support concurrent processing of 100+ invoices
