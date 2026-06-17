package com.act.hospitalmanagementsystem.billing.service;

import com.act.hospitalmanagementsystem.billing.dto.PaymentDTO;
import com.act.hospitalmanagementsystem.billing.dto.ProcessPaymentRequest;
import com.act.hospitalmanagementsystem.billing.entity.Invoice;
import com.act.hospitalmanagementsystem.billing.entity.Payment;
import com.act.hospitalmanagementsystem.billing.entity.Refund;
import com.act.hospitalmanagementsystem.billing.enums.PaymentStatus;
import com.act.hospitalmanagementsystem.billing.enums.RefundStatus;
import com.act.hospitalmanagementsystem.billing.mapper.BillingMapper;
import com.act.hospitalmanagementsystem.billing.repository.PaymentRepository;
import com.act.hospitalmanagementsystem.billing.repository.RefundRepository;
import com.act.hospitalmanagementsystem.common.exception.BadRequestException;
import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RefundRepository refundRepository;
    private final BillingMapper billingMapper;
    private final InvoiceService invoiceService;

    @Transactional
    public PaymentDTO processPayment(ProcessPaymentRequest request) {
        Invoice invoice = invoiceService.findOrThrow(request.getInvoiceId());

        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setPatientId(invoice.getPatientId());
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setAmount(request.getAmount());
        payment.setReferenceNumber(request.getReferenceNumber());
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setNotes(request.getNotes());

        Payment saved = paymentRepository.save(payment);

        // Update invoice paid/balance amounts
        BigDecimal totalPaid = paymentRepository.sumPaidByInvoice(invoice.getId());
        invoice.setPaidAmount(totalPaid);
        invoiceService.updateInvoiceBalance(invoice.getId());

        return billingMapper.toDTO(saved);
    }

    public Page<PaymentDTO> getAllPayments(Pageable pageable) {
        return paymentRepository.findByDeletedFalse(pageable).map(billingMapper::toDTO);
    }

    public List<PaymentDTO> getPaymentsByInvoice(UUID invoiceId) {
        return paymentRepository.findByInvoiceIdAndDeletedFalse(invoiceId)
                .stream().map(billingMapper::toDTO).collect(Collectors.toList());
    }

    public Page<PaymentDTO> getPaymentsByPatient(UUID patientId, Pageable pageable) {
        return paymentRepository.findByPatientIdAndDeletedFalse(patientId, pageable).map(billingMapper::toDTO);
    }

    @Transactional
    public PaymentDTO processRefund(UUID paymentId, BigDecimal amount, String reason) {
        Payment original = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", paymentId));

        if (amount.compareTo(original.getAmount()) > 0) {
            throw new BadRequestException("Refund amount cannot exceed original payment amount", "INVALID_REFUND");
        }

        Refund refund = new Refund();
        refund.setPayment(original);
        refund.setInvoice(original.getInvoice());
        refund.setPatientId(original.getPatientId());
        refund.setRefundDate(LocalDate.now());
        refund.setAmount(amount);
        refund.setReason(reason);
        refund.setStatus(RefundStatus.PROCESSED);
        refundRepository.save(refund);

        original.setStatus(PaymentStatus.REFUNDED);
        return billingMapper.toDTO(paymentRepository.save(original));
    }
}
