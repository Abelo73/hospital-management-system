package com.act.hospitalmanagementsystem.billing.controller;

import com.act.hospitalmanagementsystem.billing.dto.CreateInvoiceRequest;
import com.act.hospitalmanagementsystem.billing.dto.InvoiceDTO;
import com.act.hospitalmanagementsystem.billing.enums.InvoiceStatus;
import com.act.hospitalmanagementsystem.billing.service.InvoiceService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/billing/invoices")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('BILLING_READ')")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    @PreAuthorize("hasAuthority('BILLING_WRITE')")
    public ResponseEntity<BaseResponseDTO<InvoiceDTO>> createInvoice(@Valid @RequestBody CreateInvoiceRequest request) {
        return ResponseEntity.ok(BaseResponseDTO.success("Invoice created successfully", invoiceService.createInvoice(request)));
    }

    @GetMapping
    public ResponseEntity<BaseResponseDTO<Page<InvoiceDTO>>> getAllInvoices(Pageable pageable) {
        return ResponseEntity.ok(BaseResponseDTO.success(invoiceService.getAllInvoices(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDTO<InvoiceDTO>> getInvoice(@PathVariable UUID id) {
        return ResponseEntity.ok(BaseResponseDTO.success(invoiceService.getById(id)));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<BaseResponseDTO<Page<InvoiceDTO>>> getPatientInvoices(@PathVariable UUID patientId, Pageable pageable) {
        return ResponseEntity.ok(BaseResponseDTO.success(invoiceService.getByPatient(patientId, pageable)));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<BaseResponseDTO<Page<InvoiceDTO>>> getByStatus(@PathVariable InvoiceStatus status, Pageable pageable) {
        return ResponseEntity.ok(BaseResponseDTO.success(invoiceService.getByStatus(status, pageable)));
    }

    @PostMapping("/{id}/finalize")
    @PreAuthorize("hasAuthority('BILLING_WRITE')")
    public ResponseEntity<BaseResponseDTO<InvoiceDTO>> finalizeInvoice(@PathVariable UUID id) {
        return ResponseEntity.ok(BaseResponseDTO.success("Invoice finalized", invoiceService.finalizeInvoice(id)));
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('BILLING_WRITE')")
    public ResponseEntity<BaseResponseDTO<InvoiceDTO>> cancelInvoice(@PathVariable UUID id) {
        return ResponseEntity.ok(BaseResponseDTO.success("Invoice cancelled", invoiceService.cancelInvoice(id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('BILLING_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteInvoice(@PathVariable UUID id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok(BaseResponseDTO.<Void>success("Invoice deleted", null));
    }
}
