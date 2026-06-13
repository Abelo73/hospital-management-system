# Inventory Module

## Overview

The Inventory module provides comprehensive inventory management capabilities for the Hospital Management System. It handles pharmaceutical drugs, medical supplies, equipment, and consumables, ensuring optimal stock levels, proper tracking, and efficient procurement processes.

---

## Features

### 1. Item Management
- **Item Registration** - Register new inventory items with complete details
- **Item Categories** - Organize items by category (drugs, supplies, equipment, etc.)
- **Item Specifications** - Manage item specifications, manufacturers, and suppliers
- **Batch Management** - Track items by batch/lot numbers
- **Expiry Tracking** - Monitor expiry dates and generate alerts
- **Item Images** - Store item images for easy identification

### 2. Stock Management
- **Stock Levels** - Track current stock levels in real-time
- **Reorder Points** - Set and monitor reorder points
- **Safety Stock** - Maintain safety stock levels
- **Stock Adjustment** - Adjust stock levels for discrepancies
- **Stock Transfer** - Transfer stock between locations/departments
- **Physical Count** - Conduct physical inventory counts

### 3. Procurement Management
- **Purchase Requisitions** - Create and manage purchase requisitions
- **Purchase Orders** - Generate and track purchase orders
- **Supplier Management** - Manage supplier information and performance
- **Quotation Management** - Request and compare supplier quotations
- **Goods Receipt** - Receive and verify goods from suppliers
- **Invoice Processing** - Process supplier invoices

### 4. Distribution Management
- **Department Requests** - Handle inventory requests from departments
- **Issue Management** - Issue items to departments
- **Return Management** - Process returns from departments
- **Consumption Tracking** - Track item consumption by department
- **Cost Allocation** - Allocate costs to departments

### 5. Expiry Management
- **Expiry Alerts** - Generate alerts for items nearing expiry
- **Expiry Reports** - Generate expiry reports
- **Disposal Management** - Manage disposal of expired items
- **Return to Supplier** - Process returns of expired items
- **Expiry Analysis** - Analyze expiry patterns

### 6. Reporting and Analytics
- **Stock Reports** - Generate stock status and valuation reports
- **Movement Reports** - Track item movements and transactions
- **Consumption Reports** - Analyze consumption patterns
- **Supplier Performance** - Evaluate supplier performance
- **Cost Analysis** - Analyze inventory costs
- **Forecasting** - Forecast future inventory needs

### 7. Multi-Location Support
- **Location Management** - Manage multiple storage locations
- **Warehouse Management** - Manage warehouse operations
- **Department Stock** - Track stock at department level
- **Transfer Management** - Transfer stock between locations
- **Consolidation** - Consolidate stock across locations

### 8. Integration
- **Pharmacy Integration** - Integrate with pharmacy module for drug dispensing
- **Laboratory Integration** - Integrate with lab module for reagent tracking
- **Billing Integration** - Integrate with billing for cost allocation
- **Procurement Integration** - Integrate with finance for purchase order processing

---

## Architecture

### Components

```
inventory/
├── controller/
│   ├── ItemController.java               # Item management endpoints
│   ├── StockController.java              # Stock management endpoints
│   ├── ProcurementController.java       # Procurement management endpoints
│   ├── DistributionController.java      # Distribution management endpoints
│   ├── ExpiryController.java             # Expiry management endpoints
│   ├── ReportController.java             # Reporting endpoints
│   └── LocationController.java           # Location management endpoints
├── service/
│   ├── ItemService.java                  # Item management business logic
│   ├── StockService.java                 # Stock management business logic
│   ├── ProcurementService.java           # Procurement management business logic
│   ├── DistributionService.java          # Distribution management business logic
│   ├── ExpiryService.java                # Expiry management business logic
│   ├── ReportService.java                # Reporting business logic
│   └── LocationService.java              # Location management business logic
├── repository/
│   ├── ItemRepository.java               # Item data access
│   ├── StockRepository.java              # Stock data access
│   ├── BatchRepository.java              # Batch data access
│   ├── ProcurementRepository.java        # Procurement data access
│   ├── SupplierRepository.java           # Supplier data access
│   ├── DistributionRepository.java       # Distribution data access
│   └── LocationRepository.java           # Location data access
├── entity/
│   ├── Item.java                         # Item entity
│   ├── ItemCategory.java                 # Item category entity
│   ├── Stock.java                        # Stock entity
│   ├── Batch.java                        # Batch entity
│   ├── Supplier.java                     # Supplier entity
│   ├── PurchaseOrder.java                # Purchase order entity
│   ├── PurchaseRequisition.java          # Purchase requisition entity
│   ├── GoodsReceipt.java                 # Goods receipt entity
│   ├── DepartmentRequest.java           # Department request entity
│   ├── StockIssue.java                   # Stock issue entity
│   ├── StockReturn.java                  # Stock return entity
│   ├── ExpiryAlert.java                  # Expiry alert entity
│   └── Location.java                     # Location entity
├── enums/
│   ├── ItemType.java                     # Item type enum
│   ├── StockStatus.java                  # Stock status enum
│   ├── ProcurementStatus.java            # Procurement status enum
│   ├── RequestStatus.java                # Request status enum
│   ├── ExpiryStatus.java                # Expiry status enum
│   └── LocationType.java                # Location type enum
├── dto/
│   ├── ItemDTO.java                     # Item DTO
│   ├── CreateItemRequest.java            # Create item request
│   ├── UpdateItemRequest.java            # Update item request
│   ├── StockDTO.java                    # Stock DTO
│   ├── PurchaseOrderDTO.java            # Purchase order DTO
│   ├── PurchaseRequisitionDTO.java      # Purchase requisition DTO
│   ├── SupplierDTO.java                 # Supplier DTO
│   ├── DepartmentRequestDTO.java        # Department request DTO
│   ├── StockIssueDTO.java               # Stock issue DTO
│   └── ExpiryAlertDTO.java              # Expiry alert DTO
└── mapper/
    ├── ItemMapper.java                  # Item mapper
    ├── StockMapper.java                 # Stock mapper
    ├── ProcurementMapper.java           # Procurement mapper
    └── DistributionMapper.java          # Distribution mapper
```

### Data Model

#### Item Entity
- `id` (UUID) - Primary key
- `itemCode` (String) - Unique item code/SKU
- `itemName` (String) - Item name
- `itemType` (ItemType) - Type of item (DRUG, SUPPLY, EQUIPMENT, CONSUMABLE)
- `category` (ItemCategory) - Item category
- `description` (String) - Item description
- `manufacturer` (String) - Manufacturer name
- `brand` (String) - Brand name
- `model` (String) - Model number
- `unitOfMeasure` (String) - Unit of measure (tablet, ml, piece, etc.)
- `packSize` (Integer) - Pack size
- `minimumOrderQuantity` (Integer) - Minimum order quantity
- `reorderLevel` (Integer) - Reorder level
- `safetyStock` (Integer) - Safety stock level
- `maximumStock` (Integer) - Maximum stock level
- `leadTimeDays` (Integer) - Lead time in days
- `shelfLifeDays` (Integer) - Shelf life in days
- `storageConditions` (String) - Required storage conditions
- `isControlledSubstance` (Boolean) - Whether item is a controlled substance
- `requiresPrescription` (Boolean) - Whether item requires prescription
- `isColdChain` (Boolean) - Whether item requires cold chain
- `imageUrl` (String) - URL to item image
- `specifications` (String) - Item specifications (JSON)
- `isActive` (Boolean) - Whether item is active
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### Stock Entity
- `id` (UUID) - Primary key
- `item` (Item) - Associated item
- `location` (Location) - Storage location
- `batch` (Batch) - Associated batch
- `quantity` (Integer) - Current quantity
- `availableQuantity` (Integer) - Available quantity (excluding reserved)
- `reservedQuantity` (Integer) - Reserved quantity
- `unitCost` (Double) - Unit cost
- `totalCost` (Double) - Total cost
- `expiryDate` (LocalDate) - Expiry date
- `manufactureDate` (LocalDate) - Manufacture date
- `lastReceivedDate` (LocalDate) - Last received date
- `lastIssuedDate` (LocalDate) - Last issued date
- `status` (StockStatus) - Current status (AVAILABLE, RESERVED, EXPIRED, DAMAGED, QUARANTINED)
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### Batch Entity
- `id` (UUID) - Primary key
- `batchNumber` (String) - Batch/lot number
- `item` (Item) - Associated item
- `manufacturer` (String) - Manufacturer
- `manufactureDate` (LocalDate) - Manufacture date
- `expiryDate` (LocalDate) - Expiry date
- `quantity` (Integer) - Total quantity in batch
- `receivedDate` (LocalDate) - Date received
- `supplier` (Supplier) - Supplier
- `purchaseOrder` (PurchaseOrder) - Purchase order
- `costPerUnit` (Double) - Cost per unit
- `totalCost` (Double) - Total cost
- `storageLocation` (String) - Storage location
- `qualityCheckStatus` (String) - Quality check status
- `qualityCheckDate` (LocalDate) - Quality check date
- `notes` (String) - Additional notes
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### Supplier Entity
- `id` (UUID) - Primary key
- `supplierCode` (String) - Unique supplier code
- `supplierName` (String) - Supplier name
- `contactPerson` (String) - Contact person
- `email` (String) - Email address
- `phoneNumber` (String) - Phone number
- `address` (String) - Address
- `city` (String) - City
- `state` (String) - State
- `country` (String) - Country
- `postalCode` (String) - Postal code
- `taxId` (String) - Tax ID
- `paymentTerms` (String) - Payment terms
- `deliveryTerms` (String) - Delivery terms
- `creditLimit` (Double) - Credit limit
- `rating` (Double) - Supplier rating
- `isActive` (Boolean) - Whether supplier is active
- `notes` (String) - Additional notes
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### PurchaseOrder Entity
- `id` (UUID) - Primary key
- `orderNumber` (String) - Unique order number
- `supplier` (Supplier) - Supplier
- `orderDate` (LocalDate) - Order date
- `expectedDeliveryDate` (LocalDate) - Expected delivery date
- `actualDeliveryDate` (LocalDate) - Actual delivery date
- `status` (ProcurementStatus) - Current status (DRAFT, PENDING_APPROVAL, APPROVED, ORDERED, PARTIALLY_RECEIVED, RECEIVED, CANCELLED)
- `totalAmount` (Double) - Total order amount
- `taxAmount` (Double) - Tax amount
- `discountAmount` (Double) - Discount amount
- `netAmount` (Double) - Net amount
- `currency` (String) - Currency code
- `paymentMethod` (String) - Payment method
- `deliveryMethod` (String) - Delivery method
- `shippingAddress` (String) - Shipping address
- `billingAddress` (String) - Billing address
- `notes` (String) - Additional notes
- `approvedBy` (UUID) - User who approved
- `approvedOn` (LocalDateTime) - Approval date
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### DepartmentRequest Entity
- `id` (UUID) - Primary key
- `requestNumber` (String) - Unique request number
- `department` (String) - Requesting department
- `requestedBy` (UUID) - User who requested
- `requestDate` (LocalDate) - Request date
- `requiredDate` (LocalDate) - Required by date
- `status` (RequestStatus) - Current status (PENDING, APPROVED, PARTIALLY_ISSUED, ISSUED, REJECTED, CANCELLED)
- `priority` (String) - Priority level (LOW, MEDIUM, HIGH, URGENT)
- `purpose` (String) - Purpose of request
- `items` (String) - Requested items (JSON)
- `totalQuantity` (Integer) - Total quantity requested
- `totalAmount` (Double) - Total estimated amount
- `approvedBy` (UUID) - User who approved
- `approvedOn` (LocalDateTime) - Approval date
- `notes` (String) - Additional notes
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### StockIssue Entity
- `id` (UUID) - Primary key
- `issueNumber` (String) - Unique issue number
- `departmentRequest` (DepartmentRequest) - Associated department request
- `department` (String) - Issuing department
- `issuedBy` (UUID) - User who issued
- `issueDate` (LocalDate) - Issue date
- `items` (String) - Issued items (JSON)
- `totalQuantity` (Integer) - Total quantity issued
- `totalAmount` (Double) - Total amount
- `receivedBy` (UUID) - User who received
- `receivedOn` (LocalDateTime) - Receipt date
- `notes` (String) - Additional notes
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### ExpiryAlert Entity
- `id` (UUID) - Primary key
- `item` (Item) - Associated item
- `batch` (Batch) - Associated batch
- `location` (Location) - Location
- `expiryDate` (LocalDate) - Expiry date
- `daysToExpiry` (Integer) - Days until expiry
- `quantity` (Integer) - Quantity expiring
- `alertType` (String) - Alert type (WARNING, CRITICAL, EXPIRED)
- `alertDate` (LocalDate) - Date when alert was generated
- `isAcknowledged` (Boolean) - Whether alert was acknowledged
- `acknowledgedBy` (UUID) - User who acknowledged
- `acknowledgedOn` (LocalDateTime) - Acknowledgment date
- `actionTaken` (String) - Action taken
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### Location Entity
- `id` (UUID) - Primary key
- `locationCode` (String) - Unique location code
- `locationName` (String) - Location name
- `locationType` (LocationType) - Type of location (WAREHOUSE, PHARMACY, DEPARTMENT, STORE)
- `parentLocation` (Location) - Parent location (for hierarchical locations)
- `address` (String) - Address
- `capacity` (Integer) - Storage capacity
- `currentUtilization` (Integer) - Current utilization
- `manager` (UUID) - Location manager
- `isActive` (Boolean) - Whether location is active
- `notes` (String) - Additional notes
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

---

## API Endpoints

### Item Management Endpoints

#### Get All Items
```http
GET /api/inventory/items?page=0&size=20&category=DRUG&status=ACTIVE
Authorization: Bearer {token}
```

#### Get Item by ID
```http
GET /api/inventory/items/{id}
Authorization: Bearer {token}
```

#### Get Item by Code
```http
GET /api/inventory/items/code/{itemCode}
Authorization: Bearer {token}
```

#### Create Item
```http
POST /api/inventory/items
Authorization: Bearer {token}
Content-Type: application/json

{
  "itemCode": "DRUG001",
  "itemName": "Paracetamol 500mg",
  "itemType": "DRUG",
  "category": "ANALGESIC",
  "description": "Paracetamol 500mg tablets",
  "manufacturer": "Pharma Corp",
  "unitOfMeasure": "tablet",
  "packSize": 100,
  "minimumOrderQuantity": 100,
  "reorderLevel": 500,
  "safetyStock": 200,
  "leadTimeDays": 7,
  "shelfLifeDays": 730,
  "requiresPrescription": false
}
```

#### Update Item
```http
PUT /api/inventory/items/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "reorderLevel": 600,
  "safetyStock": 250
}
```

#### Search Items
```http
GET /api/inventory/items/search?query=paracetamol&page=0&size=20
Authorization: Bearer {token}
```

### Stock Management Endpoints

#### Get Stock Status
```http
GET /api/inventory/stock?location=ALL&category=DRUG
Authorization: Bearer {token}
```

#### Get Stock by Item
```http
GET /api/inventory/stock/item/{itemId}
Authorization: Bearer {token}
```

#### Get Low Stock Items
```http
GET /api/inventory/stock/low-stock?location=ALL
Authorization: Bearer {token}
```

#### Adjust Stock
```http
POST /api/inventory/stock/adjust
Authorization: Bearer {token}
Content-Type: application/json

{
  "itemId": "uuid",
  "locationId": "uuid",
  "batchId": "uuid",
  "adjustmentQuantity": 10,
  "reason": "Physical count adjustment",
  "adjustmentType": "INCREASE"
}
```

#### Transfer Stock
```http
POST /api/inventory/stock/transfer
Authorization: Bearer {token}
Content-Type: application/json

{
  "itemId": "uuid",
  "fromLocationId": "uuid",
  "toLocationId": "uuid",
  "quantity": 50,
  "reason": "Department request"
}
```

### Procurement Endpoints

#### Get Suppliers
```http
GET /api/inventory/suppliers?page=0&size=20&status=ACTIVE
Authorization: Bearer {token}
```

#### Create Supplier
```http
POST /api/inventory/suppliers
Authorization: Bearer {token}
Content-Type: application/json

{
  "supplierCode": "SUP001",
  "supplierName": "Pharma Distributors Ltd",
  "contactPerson": "John Smith",
  "email": "john@pharmadist.com",
  "phoneNumber": "+1234567890",
  "address": "123 Industrial Area",
  "city": "Nairobi",
  "paymentTerms": "NET 30",
  "creditLimit": 100000.00
}
```

#### Create Purchase Requisition
```http
POST /api/inventory/procurement/requisitions
Authorization: Bearer {token}
Content-Type: application/json

{
  "items": [
    {
      "itemId": "uuid",
      "quantity": 1000,
      "unitCost": 0.50
    }
  ],
  "priority": "HIGH",
  "requiredDate": "2026-07-15",
  "purpose": "Restock"
}
```

#### Create Purchase Order
```http
POST /api/inventory/procurement/purchase-orders
Authorization: Bearer {token}
Content-Type: application/json

{
  "supplierId": "uuid",
  "items": [
    {
      "itemId": "uuid",
      "quantity": 1000,
      "unitCost": 0.50
    }
  ],
  "expectedDeliveryDate": "2026-07-15",
  "paymentMethod": "CREDIT",
  "deliveryMethod": "SUPPLIER_DELIVERY"
}
```

#### Receive Goods
```http
POST /api/inventory/procurement/goods-receipt
Authorization: Bearer {token}
Content-Type: application/json

{
  "purchaseOrderId": "uuid",
  "items": [
    {
      "itemId": "uuid",
      "quantityReceived": 950,
      "batchNumber": "BATCH001",
      "expiryDate": "2028-06-30",
      "manufactureDate": "2026-06-01"
    }
  ],
  "receivedBy": "uuid",
  "notes": "Partial delivery received"
}
```

### Distribution Endpoints

#### Create Department Request
```http
POST /api/inventory/distribution/requests
Authorization: Bearer {token}
Content-Type: application/json

{
  "department": "CARDIOLOGY",
  "requiredDate": "2026-06-20",
  "priority": "MEDIUM",
  "purpose": "Routine restock",
  "items": [
    {
      "itemId": "uuid",
      "quantity": 100
    }
  ]
}
```

#### Get Pending Requests
```http
GET /api/inventory/distribution/requests?status=PENDING
Authorization: Bearer {token}
```

#### Approve Request
```http
POST /api/inventory/distribution/requests/{id}/approve
Authorization: Bearer {managerToken}
Content-Type: application/json

{
  "approved": true,
  "notes": "Approved for issuance"
}
```

#### Issue Stock
```http
POST /api/inventory/distribution/issues
Authorization: Bearer {token}
Content-Type: application/json

{
  "requestId": "uuid",
  "items": [
    {
      "itemId": "uuid",
      "batchId": "uuid",
      "quantity": 100
    }
  ],
  "receivedBy": "uuid"
}
```

### Expiry Management Endpoints

#### Get Expiry Alerts
```http
GET /api/inventory/expiry/alerts?days=30&location=ALL
Authorization: Bearer {token}
```

#### Get Expiring Items
```http
GET /api/inventory/expiry/items?days=30&location=ALL
Authorization: Bearer {token}
```

#### Acknowledge Alert
```http
POST /api/inventory/expiry/alerts/{id}/acknowledge
Authorization: Bearer {token}
Content-Type: application/json

{
  "actionTaken": "Planned for disposal"
}
```

#### Dispose Expired Items
```http
POST /api/inventory/expiry/dispose
Authorization: Bearer {token}
Content-Type: application/json

{
  "items": [
    {
      "batchId": "uuid",
      "quantity": 50,
      "disposalMethod": "INCINERATION",
      "reason": "Expired"
    }
  ],
  "disposedBy": "uuid"
}
```

### Reporting Endpoints

#### Generate Stock Report
```http
POST /api/inventory/reports/stock
Authorization: Bearer {token}
Content-Type: application/json

{
  "location": "ALL",
  "category": "DRUG",
  "format": "PDF",
  "includeZeroStock": false
}
```

#### Generate Movement Report
```http
POST /api/inventory/reports/movement
Authorization: Bearer {token}
Content-Type: application/json

{
  "startDate": "2026-06-01",
  "endDate": "2026-06-30",
  "itemType": "DRUG",
  "format": "EXCEL"
}
```

#### Generate Consumption Report
```http
POST /api/inventory/reports/consumption
Authorization: Bearer {token}
Content-Type: application/json

{
  "startDate": "2026-06-01",
  "endDate": "2026-06-30",
  "department": "ALL",
  "format": "PDF"
}
```

---

## Testing Flow Scenarios

### Scenario 1: Item Registration

**Steps:**
1. Login as inventory manager
2. Create a new item
3. Set reorder levels and safety stock
4. Verify item is created
5. Search for the item

**Test Commands:**
```bash
# Login as inventory manager
INVENTORY_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "inventory_manager", "password": "password"}' \
  | jq -r '.data.accessToken')

# Create item
curl -X POST http://localhost:8080/api/inventory/items \
  -H "Authorization: Bearer $INVENTORY_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "itemCode": "DRUG001",
    "itemName": "Paracetamol 500mg",
    "itemType": "DRUG",
    "category": "ANALGESIC",
    "description": "Paracetamol 500mg tablets",
    "manufacturer": "Pharma Corp",
    "unitOfMeasure": "tablet",
    "packSize": 100,
    "minimumOrderQuantity": 100,
    "reorderLevel": 500,
    "safetyStock": 200,
    "leadTimeDays": 7,
    "shelfLifeDays": 730,
    "requiresPrescription": false
  }'

# Expected: 200 OK with item details
```

---

### Scenario 2: Procurement Process

**Steps:**
1. Login as inventory manager
2. Create supplier
3. Create purchase requisition
4. Convert to purchase order
5. Receive goods
6. Verify stock is updated

**Test Commands:**
```bash
# Login as inventory manager
INVENTORY_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "inventory_manager", "password": "password"}' \
  | jq -r '.data.accessToken')

# Create supplier
SUPPLIER_RESPONSE=$(curl -X POST http://localhost:8080/api/inventory/suppliers \
  -H "Authorization: Bearer $INVENTORY_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "supplierCode": "SUP001",
    "supplierName": "Pharma Distributors Ltd",
    "contactPerson": "John Smith",
    "email": "john@pharmadist.com",
    "phoneNumber": "+1234567890",
    "paymentTerms": "NET 30"
  }')

SUPPLIER_ID=$(echo $SUPPLIER_RESPONSE | jq -r '.data.id')

# Create purchase order
curl -X POST http://localhost:8080/api/inventory/procurement/purchase-orders \
  -H "Authorization: Bearer $INVENTORY_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "supplierId": "'$SUPPLIER_ID'",
    "items": [
      {
        "itemId": "uuid",
        "quantity": 1000,
        "unitCost": 0.50
      }
    ],
    "expectedDeliveryDate": "2026-07-15",
    "paymentMethod": "CREDIT"
  }'

# Expected: 200 OK with purchase order details
```

---

### Scenario 3: Department Request and Issue

**Steps:**
1. Login as department staff
2. Create department request
3. Login as inventory manager
4. Approve request
5. Issue stock
6. Verify stock is deducted

**Test Commands:**
```bash
# Login as department staff
DEPT_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "cardiology_staff", "password": "password"}' \
  | jq -r '.data.accessToken')

# Create department request
REQUEST_RESPONSE=$(curl -X POST http://localhost:8080/api/inventory/distribution/requests \
  -H "Authorization: Bearer $DEPT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "department": "CARDIOLOGY",
    "requiredDate": "2026-06-20",
    "priority": "MEDIUM",
    "purpose": "Routine restock",
    "items": [
      {
        "itemId": "uuid",
        "quantity": 100
      }
    ]
  }')

REQUEST_ID=$(echo $REQUEST_RESPONSE | jq -r '.data.id')

# Login as inventory manager
INVENTORY_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "inventory_manager", "password": "password"}' \
  | jq -r '.data.accessToken')

# Approve request
curl -X POST http://localhost:8080/api/inventory/distribution/requests/$REQUEST_ID/approve \
  -H "Authorization: Bearer $INVENTORY_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "approved": true,
    "notes": "Approved for issuance"
  }'

# Expected: 200 OK
```

---

### Scenario 4: Expiry Management

**Steps:**
1. Login as inventory manager
2. Get expiry alerts
3. Acknowledge alerts
4. Dispose expired items
5. Generate expiry report

**Test Commands:**
```bash
# Login as inventory manager
INVENTORY_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "inventory_manager", "password": "password"}' \
  | jq -r '.data.accessToken')

# Get expiry alerts
curl -X GET "http://localhost:8080/api/inventory/expiry/alerts?days=30&location=ALL" \
  -H "Authorization: Bearer $INVENTORY_TOKEN"

# Acknowledge alert
curl -X POST http://localhost:8080/api/inventory/expiry/alerts/{id}/acknowledge \
  -H "Authorization: Bearer $INVENTORY_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "actionTaken": "Planned for disposal"
  }'

# Expected: 200 OK
```

---

## Security Considerations

### Access Control
- Inventory management requires INVENTORY_WRITE permission
- Purchase order approval requires INVENTORY_ADMIN permission
- Supplier management requires INVENTORY_ADMIN permission
- Cost information is restricted to authorized users

### Data Integrity
- Stock adjustments require proper authorization
- Purchase orders above threshold require approval
- Controlled substances have additional restrictions
- Audit logging for all inventory transactions

### Compliance
- Track controlled substances as per regulations
- Maintain proper expiry tracking for drugs
- Document disposal of expired items
- Comply with pharmaceutical regulations

---

## Dependencies

### Internal Dependencies
- `auth` - For user authentication and authorization
- `common` - For shared utilities and DTOs
- `notification` - For sending expiry alerts (when implemented)
- `pharmacy` - For drug dispensing integration (when implemented)
- `billing` - For cost allocation integration

### External Dependencies
- Spring Batch - For batch processing of stock updates
- Apache POI - For Excel report generation
- iText - For PDF report generation
- Barcode/QR Code library - For item scanning (optional)
- Email service - For supplier communications (optional)

---

## Future Enhancements

### Planned Features
- Barcode/QR code scanning for items
- Automated reorder suggestions
- Integration with supplier systems
- Mobile app for stock counting
- Real-time stock synchronization
- Predictive analytics for demand forecasting
- Integration with pharmacy dispensing
- Automated expiry alerts via notifications

### Performance Improvements
- Implement caching for frequently accessed items
- Use database indexes for faster queries
- Optimize batch processing for large stock updates
- Implement query result caching

---

## Notes

- This module is critical for hospital operations and patient care
- Ensure proper tracking of controlled substances
- Implement regular stock audits
- Maintain proper documentation for regulatory compliance
- Consider implementing FIFO (First In, First Out) for stock issuance
- Regularly review and update reorder levels based on consumption patterns
