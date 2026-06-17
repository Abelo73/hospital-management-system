package com.act.hospitalmanagementsystem.billing.controller;

import com.act.hospitalmanagementsystem.billing.dto.PaymentDTO;
import com.act.hospitalmanagementsystem.billing.dto.ProcessPaymentRequest;
import com.act.hospitalmanagementsystem.billing.service.PaymentService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/billing/payments")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('BILLING_READ')")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @PreAuthorize("hasAuthority('BILLING_WRITE')")
    public ResponseEntity<BaseResponseDTO<PaymentDTO>> processPayment(@Valid @RequestBody ProcessPaymentRequest request) {
        return ResponseEntity.ok(BaseResponseDTO.success("Payment processed successfully", paymentService.processPayment(request)));
    }

    @GetMapping
    public ResponseEntity<BaseResponseDTO<Page<PaymentDTO>>> getAllPayments(Pageable pageable) {
        return ResponseEntity.ok(BaseResponseDTO.success(paymentService.getAllPayments(pageable)));
    }

    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<BaseResponseDTO<List<PaymentDTO>>> getInvoicePayments(@PathVariable UUID invoiceId) {
        return ResponseEntity.ok(BaseResponseDTO.success(paymentService.getPaymentsByInvoice(invoiceId)));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<BaseResponseDTO<Page<PaymentDTO>>> getPatientPayments(@PathVariable UUID patientId, Pageable pageable) {
        return ResponseEntity.ok(BaseResponseDTO.success(paymentService.getPaymentsByPatient(patientId, pageable)));
    }

    @PostMapping("/{paymentId}/refund")
    @PreAuthorize("hasAuthority('BILLING_WRITE')")
    public ResponseEntity<BaseResponseDTO<PaymentDTO>> refundPayment(
            @PathVariable UUID paymentId,
            @RequestBody Map<String, Object> body) {
        BigDecimal amount = new BigDecimal(body.get("amount").toString());
        String reason = (String) body.getOrDefault("reason", "");
        return ResponseEntity.ok(BaseResponseDTO.success("Refund processed", paymentService.processRefund(paymentId, amount, reason)));
    }
}
