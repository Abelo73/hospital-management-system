package com.act.hospitalmanagementsystem.billing.service;

import com.act.hospitalmanagementsystem.billing.dto.CreateInvoiceRequest;
import com.act.hospitalmanagementsystem.billing.dto.InvoiceDTO;
import com.act.hospitalmanagementsystem.billing.entity.Invoice;
import com.act.hospitalmanagementsystem.billing.entity.InvoiceLineItem;
import com.act.hospitalmanagementsystem.billing.enums.InvoiceStatus;
import com.act.hospitalmanagementsystem.billing.mapper.BillingMapper;
import com.act.hospitalmanagementsystem.billing.repository.InvoiceRepository;
import com.act.hospitalmanagementsystem.common.exception.BadRequestException;
import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final BillingMapper billingMapper;

    @Transactional
    public InvoiceDTO createInvoice(CreateInvoiceRequest request) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(generateInvoiceNumber());
        invoice.setPatientId(request.getPatientId());
        invoice.setInvoiceDate(request.getInvoiceDate() != null ? request.getInvoiceDate() : LocalDate.now());
        invoice.setDueDate(request.getDueDate() != null ? request.getDueDate() : LocalDate.now().plusDays(30));
        invoice.setStatus(InvoiceStatus.DRAFT);
        invoice.setPaymentMethod(request.getPaymentMethod());
        invoice.setNotes(request.getNotes());

        List<InvoiceLineItem> items = request.getLineItems().stream().map(req -> {
            InvoiceLineItem item = new InvoiceLineItem();
            item.setInvoice(invoice);
            item.setServiceType(req.getServiceType());
            item.setServiceId(req.getServiceId());
            item.setDescription(req.getDescription());
            item.setQuantity(req.getQuantity() != null ? req.getQuantity() : 1);
            item.setUnitPrice(req.getUnitPrice());
            item.setLineTotal(req.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            return item;
        }).collect(Collectors.toList());

        invoice.setLineItems(items);
        recalculate(invoice);
        return billingMapper.toDTO(invoiceRepository.save(invoice));
    }

    public Page<InvoiceDTO> getAllInvoices(Pageable pageable) {
        return invoiceRepository.findByDeletedFalse(pageable).map(billingMapper::toDTO);
    }

    public InvoiceDTO getById(UUID id) {
        return billingMapper.toDTO(findOrThrow(id));
    }

    public Page<InvoiceDTO> getByPatient(UUID patientId, Pageable pageable) {
        return invoiceRepository.findByPatientIdAndDeletedFalse(patientId, pageable).map(billingMapper::toDTO);
    }

    public Page<InvoiceDTO> getByStatus(InvoiceStatus status, Pageable pageable) {
        return invoiceRepository.findByStatusAndDeletedFalse(status, pageable).map(billingMapper::toDTO);
    }

    @Transactional
    public InvoiceDTO finalizeInvoice(UUID id) {
        Invoice invoice = findOrThrow(id);
        if (invoice.getStatus() != InvoiceStatus.DRAFT) {
            throw new BadRequestException("Only DRAFT invoices can be finalized", "INVALID_STATUS");
        }
        if (invoice.getLineItems().isEmpty()) {
            throw new BadRequestException("Invoice must have at least one line item", "NO_LINE_ITEMS");
        }
        invoice.setStatus(InvoiceStatus.PENDING);
        return billingMapper.toDTO(invoiceRepository.save(invoice));
    }

    @Transactional
    public InvoiceDTO cancelInvoice(UUID id) {
        Invoice invoice = findOrThrow(id);
        if (invoice.getStatus() == InvoiceStatus.PAID) {
            throw new BadRequestException("Paid invoices cannot be cancelled", "INVALID_STATUS");
        }
        invoice.setStatus(InvoiceStatus.CANCELLED);
        return billingMapper.toDTO(invoiceRepository.save(invoice));
    }

    @Transactional
    public void deleteInvoice(UUID id) {
        Invoice invoice = findOrThrow(id);
        if (invoice.getStatus() != InvoiceStatus.DRAFT) {
            throw new BadRequestException("Only DRAFT invoices can be deleted", "INVALID_STATUS");
        }
        invoice.setDeleted(true);
        invoiceRepository.save(invoice);
    }

    @Transactional
    public void updateInvoiceBalance(UUID invoiceId) {
        Invoice invoice = findOrThrow(invoiceId);
        BigDecimal paid = invoice.getPaidAmount() != null ? invoice.getPaidAmount() : BigDecimal.ZERO;
        BigDecimal balance = invoice.getTotalAmount().subtract(paid);
        invoice.setBalanceAmount(balance);
        if (balance.compareTo(BigDecimal.ZERO) <= 0) {
            invoice.setStatus(InvoiceStatus.PAID);
        } else if (paid.compareTo(BigDecimal.ZERO) > 0) {
            invoice.setStatus(InvoiceStatus.PARTIALLY_PAID);
        }
        invoiceRepository.save(invoice);
    }

    public Invoice findOrThrow(UUID id) {
        return invoiceRepository.findById(id)
                .filter(i -> !Boolean.TRUE.equals(i.getDeleted()))
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id));
    }

    private void recalculate(Invoice invoice) {
        BigDecimal subtotal = invoice.getLineItems().stream()
                .map(InvoiceLineItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        invoice.setSubtotal(subtotal);
        BigDecimal tax = invoice.getTax() != null ? invoice.getTax() : BigDecimal.ZERO;
        BigDecimal discount = invoice.getDiscount() != null ? invoice.getDiscount() : BigDecimal.ZERO;
        invoice.setTotalAmount(subtotal.add(tax).subtract(discount));
        invoice.setPaidAmount(BigDecimal.ZERO);
        invoice.setBalanceAmount(invoice.getTotalAmount());
    }

    private String generateInvoiceNumber() {
        int seq = (invoiceRepository.findMaxInvoiceSequence() != null ? invoiceRepository.findMaxInvoiceSequence() : 0) + 1;
        return String.format("INV-%d-%04d", LocalDate.now().getYear(), seq);
    }
}
