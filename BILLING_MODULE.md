# Billing Module

## Overview
The Billing Module manages all financial transactions, invoicing, insurance claims, and payment processing for the hospital management system. This module handles the complete billing workflow from invoice generation to payment collection and insurance reimbursement.

## Core Responsibilities

### 1. Invoice Management
- **Invoice Generation**: Automatic invoice generation from appointments and procedures
- **Invoice Types**: Service invoices, procedure invoices, medication invoices, lab test invoices
- **Invoice Status**: Draft, Pending, Partially Paid, Paid, Overdue, Cancelled, Written Off
- **Line Items**: Detailed line items for each charge
- **Discount Management**: Apply discounts to invoices
- **Tax Calculation**: Automatic tax calculation
- **Invoice Templates**: Customizable invoice templates

### 2. Payment Processing
- **Payment Methods**: Cash, Credit Card, Debit Card, Insurance, Bank Transfer, Mobile Payment
- **Payment Recording**: Record payments against invoices
- **Partial Payments**: Support for partial payments
- **Payment Plans**: Create payment plans for patients
- **Refund Processing**: Process refunds for overpayments or cancellations
- **Payment Receipts**: Generate payment receipts
- **Payment History**: Complete payment history tracking

### 3. Insurance Claims
- **Claim Submission**: Submit insurance claims electronically
- **Claim Status Tracking**: Track claim status (Submitted, Pending, Approved, Rejected, Paid)
- **Claim Types**: Primary insurance, Secondary insurance, Tertiary insurance
- **Pre-authorization**: Manage pre-authorizations for procedures
- **Claim Reconciliation**: Reconcile insurance payments
- **Denial Management**: Handle claim denials and resubmissions
- **EOB Processing**: Process Explanation of Benefits (EOB)

### 4. Insurance Management
- **Insurance Providers**: Manage insurance company information
- **Patient Insurance**: Manage patient insurance policies
- **Coverage Verification**: Verify insurance coverage
- **Deductible Tracking**: Track patient deductibles
- **Co-pay Management**: Manage co-pay amounts
- **Out-of-pocket Maximum**: Track out-of-pocket maximums
- **Network Status**: Track in-network vs out-of-network status

### 5. Pricing & Tariff Management
- **Service Pricing**: Define prices for medical services
- **Procedure Pricing**: Define prices for medical procedures
- **Room Rates**: Define room rates for hospital stays
- **Medication Pricing**: Define medication prices
- **Lab Test Pricing**: Define lab test prices
- **Dynamic Pricing**: Support for dynamic pricing based on factors
- **Price Lists**: Multiple price lists for different payers

### 6. Financial Reporting
- **Revenue Reports**: Track revenue over time
- **Aging Reports**: Accounts receivable aging reports
- **Collection Reports**: Collection performance reports
- **Insurance Reports**: Insurance claim and payment reports
- **Profit & Loss**: Profit and loss statements
- **Cash Flow**: Cash flow analysis
- **Bad Debt**: Track bad debt write-offs

### 7. Patient Billing
- **Patient Statements**: Generate patient statements
- **Balance Tracking**: Track patient account balances
- **Payment Plans**: Create and manage payment plans
- **Collection Management**: Manage collections process
- **Payment Reminders**: Send payment reminders
- **Financial Assistance**: Manage financial assistance programs
- **Charity Care**: Track charity care write-offs

### 8. Audit & Compliance
- **Audit Trail**: Complete audit trail for all financial transactions
- **Compliance Reporting**: Generate compliance reports
- **Regulatory Requirements**: Meet healthcare billing regulations (HIPAA, ICD-10, CPT)
- **Fraud Detection**: Detect potential billing fraud
- **Charge Integrity**: Ensure charge integrity and accuracy

## Data Structure

### Invoice Entity
```java
- id: UUID
- invoiceNumber: String (unique)
- patientId: UUID (FK to Patient)
- appointmentId: UUID (FK to Appointment, optional)
- invoiceType: enum (SERVICE, PROCEDURE, MEDICATION, LAB_TEST, ROOM, MISCELLANEOUS)
- invoiceStatus: enum (DRAFT, PENDING, PARTIALLY_PAID, PAID, OVERDUE, CANCELLED, WRITTEN_OFF)
- invoiceDate: LocalDate
- dueDate: LocalDate
- subtotal: BigDecimal
- taxAmount: BigDecimal
- discountAmount: BigDecimal
- totalAmount: BigDecimal
- paidAmount: BigDecimal
- balanceAmount: BigDecimal
- notes: Text
- createdBy: UUID (FK to User)
- createdAt: LocalDateTime
- updatedBy: UUID (FK to User)
- updatedAt: LocalDateTime
- deleted: Boolean
```

### InvoiceLineItem Entity
```java
- id: UUID
- invoiceId: UUID (FK to Invoice)
- itemType: enum (SERVICE, PROCEDURE, MEDICATION, LAB_TEST, ROOM, MISCELLANEOUS)
- itemId: UUID (reference to service/procedure/etc)
- description: String
- quantity: Integer
- unitPrice: BigDecimal
- discountAmount: BigDecimal
- taxAmount: BigDecimal
- lineTotal: BigDecimal
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

### Payment Entity
```java
- id: UUID
- invoiceId: UUID (FK to Invoice)
- patientId: UUID (FK to Patient)
- paymentNumber: String (unique)
- paymentMethod: enum (CASH, CREDIT_CARD, DEBIT_CARD, INSURANCE, BANK_TRANSFER, MOBILE_PAYMENT)
- paymentDate: LocalDate
- amount: BigDecimal
- referenceNumber: String
- transactionId: String
- status: enum (PENDING, COMPLETED, FAILED, REFUNDED)
- notes: Text
- receivedBy: UUID (FK to User)
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

### InsuranceClaim Entity
```java
- id: UUID
- invoiceId: UUID (FK to Invoice)
- patientId: UUID (FK to Patient)
- insuranceProviderId: UUID (FK to InsuranceProvider)
- claimNumber: String
- claimType: enum (PRIMARY, SECONDARY, TERTIARY)
- claimStatus: enum (DRAFT, SUBMITTED, PENDING, APPROVED, REJECTED, PAID, PARTIALLY_PAID)
- submittedDate: LocalDate
- approvedAmount: BigDecimal
- paidAmount: BigDecimal
- denialReason: String
- notes: Text
- submittedBy: UUID (FK to User)
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

### PatientInsurance Entity
```java
- id: UUID
- patientId: UUID (FK to Patient)
- insuranceProviderId: UUID (FK to InsuranceProvider)
- policyNumber: String
- policyHolderName: String
- groupNumber: String
- memberId: String
- effectiveFromDate: LocalDate
- effectiveToDate: LocalDate
- isPrimary: Boolean
- deductibleAmount: BigDecimal
- deductibleMet: BigDecimal
- coPayAmount: BigDecimal
- outOfPocketMax: BigDecimal
- outOfPocketMet: BigDecimal
- networkStatus: enum (IN_NETWORK, OUT_OF_NETWORK)
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

### InsuranceProvider Entity
```java
- id: UUID
- name: String
- code: String
- contactPerson: String
- phoneNumber: String
- email: String
- address: String
- city: String
- state: String
- postalCode: String
- country: String
- claimsSubmissionMethod: enum (ELECTRONIC, PAPER, PORTAL)
- portalUrl: String
- isActive: Boolean
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

### PaymentPlan Entity
```java
- id: UUID
- invoiceId: UUID (FK to Invoice)
- patientId: UUID (FK to Patient)
- planName: String
- totalAmount: BigDecimal
- downPaymentAmount: BigDecimal
- numberOfInstallments: Integer
- installmentAmount: BigDecimal
- installmentFrequency: enum (WEEKLY, BI_WEEKLY, MONTHLY)
- startDate: LocalDate
- endDate: LocalDate
- status: enum (ACTIVE, COMPLETED, CANCELLED, DEFAULTED)
- notes: Text
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

### ServicePricing Entity
```java
- id: UUID
- serviceCode: String
- serviceName: String
- description: String
- category: String
- basePrice: BigDecimal
- priceList: String
- effectiveFromDate: LocalDate
- effectiveToDate: LocalDate
- isActive: Boolean
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

## API Endpoints

### Invoices
- `GET /api/invoices` - Get all invoices (with pagination)
- `GET /api/invoices/{id}` - Get specific invoice
- `GET /api/invoices/patient/{patientId}` - Get patient invoices
- `GET /api/invoices/status/{status}` - Get invoices by status
- `POST /api/invoices` - Create new invoice
- `PUT /api/invoices/{id}` - Update invoice
- `DELETE /api/invoices/{id}` - Delete invoice
- `POST /api/invoices/{id}/finalize` - Finalize invoice
- `POST /api/invoices/{id}/cancel` - Cancel invoice
- `POST /api/invoices/{id}/write-off` - Write off invoice
- `GET /api/invoices/{id}/pdf` - Generate invoice PDF
- `POST /api/invoices/search` - Search invoices

### Invoice Line Items
- `GET /api/invoices/{invoiceId}/line-items` - Get invoice line items
- `POST /api/invoices/{invoiceId}/line-items` - Add line item
- `PUT /api/invoices/{invoiceId}/line-items/{id}` - Update line item
- `DELETE /api/invoices/{invoiceId}/line-items/{id}` - Delete line item

### Payments
- `GET /api/payments` - Get all payments (with pagination)
- `GET /api/payments/{id}` - Get specific payment
- `GET /api/payments/invoice/{invoiceId}` - Get invoice payments
- `GET /api/payments/patient/{patientId}` - Get patient payments
- `POST /api/payments` - Record payment
- `PUT /api/payments/{id}` - Update payment
- `DELETE /api/payments/{id}` - Delete payment
- `POST /api/payments/{id}/refund` - Process refund
- `GET /api/payments/{id}/receipt` - Generate payment receipt

### Insurance Claims
- `GET /api/insurance-claims` - Get all claims (with pagination)
- `GET /api/insurance-claims/{id}` - Get specific claim
- `GET /api/insurance-claims/invoice/{invoiceId}` - Get invoice claims
- `GET /api/insurance-claims/patient/{patientId}` - Get patient claims
- `POST /api/insurance-claims` - Create insurance claim
- `PUT /api/insurance-claims/{id}` - Update claim
- `POST /api/insurance-claims/{id}/submit` - Submit claim
- `POST /api/insurance-claims/{id}/resubmit` - Resubmit claim
- `DELETE /api/insurance-claims/{id}` - Delete claim

### Patient Insurance
- `GET /api/patient-insurance/patient/{patientId}` - Get patient insurance policies
- `POST /api/patient-insurance` - Add patient insurance
- `PUT /api/patient-insurance/{id}` - Update patient insurance
- `DELETE /api/patient-insurance/{id}` - Delete patient insurance
- `GET /api/patient-insurance/{id}/coverage` - Verify coverage

### Insurance Providers
- `GET /api/insurance-providers` - Get all insurance providers
- `GET /api/insurance-providers/{id}` - Get specific provider
- `POST /api/insurance-providers` - Add insurance provider
- `PUT /api/insurance-providers/{id}` - Update provider
- `DELETE /api/insurance-providers/{id}` - Delete provider

### Payment Plans
- `GET /api/payment-plans` - Get all payment plans
- `GET /api/payment-plans/{id}` - Get specific payment plan
- `GET /api/payment-plans/invoice/{invoiceId}` - Get invoice payment plan
- `GET /api/payment-plans/patient/{patientId}` - Get patient payment plans
- `POST /api/payment-plans` - Create payment plan
- `PUT /api/payment-plans/{id}` - Update payment plan
- `POST /api/payment-plans/{id}/cancel` - Cancel payment plan

### Service Pricing
- `GET /api/service-pricing` - Get all service prices
- `GET /api/service-pricing/{id}` - Get specific price
- `GET /api/service-pricing/code/{code}` - Get price by code
- `POST /api/service-pricing` - Add service price
- `PUT /api/service-pricing/{id}` - Update price
- `DELETE /api/service-pricing/{id}` - Delete price

## UI Components

### Billing Dashboard
- **Financial Overview**: Key financial metrics at a glance
- **Revenue Chart**: Revenue over time visualization
- **Pending Invoices**: List of pending invoices
- **Recent Payments**: Recent payment activity
- **Aging Summary**: Accounts receivable aging summary
- **Quick Actions**: Quick invoice, payment, claim actions

### Invoice Management
- **Invoice List**: All invoices with filtering
- **Invoice Detail**: Complete invoice details
- **Line Item Management**: Add/edit/delete line items
- **Discount Application**: Apply discounts
- **Tax Calculation**: Automatic tax calculation
- **Invoice Preview**: Preview invoice before finalizing
- **PDF Generation**: Generate PDF invoices

### Payment Processing
- **Payment Entry**: Record new payments
- **Payment History**: View payment history
- **Refund Processing**: Process refunds
- **Receipt Generation**: Generate payment receipts
- **Payment Methods**: Multiple payment method support
- **Partial Payments**: Support for partial payments

### Insurance Claims
- **Claim Dashboard**: Overview of all claims
- **Claim Submission**: Submit new claims
- **Claim Tracking**: Track claim status
- **EOB Processing**: Process EOB documents
- **Denial Management**: Handle claim denials
- **Claim Reconciliation**: Reconcile insurance payments

### Patient Billing
- **Patient Account**: Patient account balance
- **Statement Generation**: Generate patient statements
- **Payment Plans**: Create payment plans
- **Collection Management**: Manage collections
- **Financial Assistance**: Apply for financial assistance
- **Payment History**: Patient payment history

### Pricing Management
- **Price List Management**: Manage service prices
- **Price Lists**: Multiple price lists
- **Bulk Updates**: Bulk price updates
- **Effective Dates**: Price effective date management
- **Price History**: Track price changes

### Financial Reports
- **Revenue Reports**: Revenue analysis
- **Aging Reports**: Accounts receivable aging
- **Collection Reports**: Collection performance
- **Insurance Reports**: Insurance claim reports
- **Profit & Loss**: P&L statements
- **Cash Flow**: Cash flow analysis

## Integration with Other Modules

### Patient Module
- **Patient Information**: Access patient demographics
- **Patient Insurance**: Access patient insurance policies
- **Billing Address**: Use patient billing address
- **Contact Information**: Use for billing communications

### Appointments Module
- **Invoice Generation**: Generate invoices from appointments
- **Service Linking**: Link services to invoices
- **Provider Information**: Use provider information for billing

### Medical History Module
- **Procedure Billing**: Bill for medical procedures
- **Medication Billing**: Bill for medications
- **Lab Test Billing**: Bill for lab tests
- **Service Linking**: Link medical services to invoices

### User/Staff Module
- **Provider Billing**: Track provider billing
- **Audit Trail**: Track who created/modified financial records
- **Role-based Access**: Different access levels for different roles

### Notification Module
- **Payment Reminders**: Send payment reminders
- **Invoice Notifications**: Send invoice notifications
- **Claim Status Updates**: Notify of claim status changes

## Security & Access Control

### Role-Based Access
- **Admin**: Full access to all billing functions
- **Billing Manager**: Manage invoices, payments, claims
- **Receptionist**: Create invoices, process payments
- **Doctor**: View billing information (read-only)
- **Patient**: View own billing (patient portal)

### Compliance & Security
- **HIPAA Compliance**: Protect patient financial information
- **PCI DSS**: Secure payment card processing
- **Audit Logging**: Complete audit trail for all financial transactions
- **Data Encryption**: Encrypt sensitive financial data
- **Access Controls**: Strict access controls to financial data

## Search & Filtering

### Advanced Search
- **Invoice Search**: Search by invoice number, patient, date
- **Payment Search**: Search by payment number, patient, date
- **Claim Search**: Search by claim number, patient, status
- **Date Range**: Filter by date ranges
- **Status Filter**: Filter by status
- **Amount Range**: Filter by amount ranges

### Saved Reports
- **Custom Reports**: Save custom report configurations
- **Scheduled Reports**: Schedule automated report generation
- **Report Distribution**: Auto-distribute reports via email

## Reporting & Analytics

### Financial Reports
- **Revenue Reports**: Revenue by period, service, provider
- **Aging Reports**: Accounts receivable aging (30, 60, 90, 120+ days)
- **Collection Reports**: Collection effectiveness metrics
- **Insurance Reports**: Claim submission, approval, payment rates
- **Profit & Loss**: Detailed P&L statements
- **Cash Flow**: Cash flow analysis and forecasting

### Key Metrics
- **Total Revenue**: Total revenue over period
- **Outstanding Balance**: Total outstanding receivables
- **Collection Rate**: Percentage of bills collected
- **Average Collection Period**: Average days to collect
- **Claim Approval Rate**: Percentage of claims approved
- **Denial Rate**: Percentage of claims denied
- **Bad Debt Write-offs**: Total bad debt written off

## Future Enhancements

### Planned Features
- **AI Billing**: AI-powered billing optimization
- **Predictive Analytics**: Predict payment behavior
- **Automated Claims**: Fully automated claim submission
- **Blockchain**: Blockchain for secure transactions
- **Mobile Payments**: Enhanced mobile payment options
- **Real-time Processing**: Real-time payment processing
- **Integration with Banks**: Direct bank integration
- **Cryptocurrency**: Accept cryptocurrency payments

## Implementation Notes

### Technology Stack
- **Backend**: Spring Boot, JPA/Hibernate, PostgreSQL
- **Frontend**: React, TypeScript, Tailwind CSS
- **Payment Gateway**: Stripe/PayPal integration
- **PDF Generation**: Apache PDFBox or similar
- **API**: RESTful API with JWT authentication
- **Database**: PostgreSQL with proper indexing for financial data

### Performance Considerations
- **Database Indexing**: Index on invoice numbers, dates, patient IDs
- **Caching**: Cache pricing data and insurance information
- **Pagination**: All list endpoints support pagination
- **Optimized Queries**: Efficient queries for financial reports
- **Transaction Management**: Proper transaction handling for financial operations

### Testing
- **Unit Tests**: Test all financial calculations
- **Integration Tests**: Test payment gateway integration
- **E2E Tests**: Test complete billing workflows
- **Security Tests**: Test for security vulnerabilities
- **Performance Tests**: Test with large datasets

## Documentation

### API Documentation
- Swagger/OpenAPI documentation
- Request/response examples
- Error codes and handling

### User Documentation
- Billing system user guides
- Payment processing guides
- Insurance claim submission guides

### Developer Documentation
- Architecture documentation
- Database schema documentation
- Code documentation
