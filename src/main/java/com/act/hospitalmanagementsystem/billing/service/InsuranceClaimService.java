package com.act.hospitalmanagementsystem.billing.service;

import com.act.hospitalmanagementsystem.billing.dto.InsuranceClaimDTO;
import com.act.hospitalmanagementsystem.billing.dto.InsuranceProviderDTO;
import com.act.hospitalmanagementsystem.billing.dto.SubmitClaimRequest;
import com.act.hospitalmanagementsystem.billing.dto.UpdateClaimStatusRequest;
import com.act.hospitalmanagementsystem.billing.entity.InsuranceClaim;
import com.act.hospitalmanagementsystem.billing.entity.InsuranceProvider;
import com.act.hospitalmanagementsystem.billing.enums.ClaimStatus;
import com.act.hospitalmanagementsystem.billing.mapper.BillingMapper;
import com.act.hospitalmanagementsystem.billing.repository.InsuranceClaimRepository;
import com.act.hospitalmanagementsystem.billing.repository.InsuranceProviderRepository;
import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsuranceClaimService {

    private final InsuranceClaimRepository claimRepository;
    private final InsuranceProviderRepository providerRepository;
    private final BillingMapper billingMapper;
    private final InvoiceService invoiceService;

    public List<InsuranceProviderDTO> getAllProviders() {
        return providerRepository.findByDeletedFalse().stream().map(billingMapper::toDTO).collect(Collectors.toList());
    }

    public List<InsuranceProviderDTO> getActiveProviders() {
        return providerRepository.findByIsActiveTrueAndDeletedFalse().stream().map(billingMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public InsuranceProviderDTO createProvider(InsuranceProviderDTO dto) {
        InsuranceProvider provider = new InsuranceProvider();
        provider.setName(dto.getName());
        provider.setContactPerson(dto.getContactPerson());
        provider.setPhone(dto.getPhone());
        provider.setEmail(dto.getEmail());
        provider.setAddress(dto.getAddress());
        provider.setClaimSubmissionMethod(dto.getClaimSubmissionMethod());
        provider.setStandardCoPay(dto.getStandardCoPay());
        provider.setStandardDeductible(dto.getStandardDeductible());
        provider.setIsActive(true);
        return billingMapper.toDTO(providerRepository.save(provider));
    }

    @Transactional
    public InsuranceClaimDTO submitClaim(SubmitClaimRequest request) {
        InsuranceClaim claim = new InsuranceClaim();
        claim.setInvoice(invoiceService.findOrThrow(request.getInvoiceId()));
        claim.setProvider(providerRepository.findById(request.getProviderId())
                .orElseThrow(() -> new ResourceNotFoundException("InsuranceProvider", "id", request.getProviderId())));
        claim.setPatientId(request.getPatientId());
        claim.setClaimNumber("CLM-" + System.currentTimeMillis());
        claim.setSubmissionDate(LocalDate.now());
        claim.setStatus(ClaimStatus.SUBMITTED);
        claim.setNotes(request.getNotes());
        return billingMapper.toDTO(claimRepository.save(claim));
    }

    public Page<InsuranceClaimDTO> getAllClaims(Pageable pageable) {
        return claimRepository.findByDeletedFalse(pageable).map(billingMapper::toDTO);
    }

    public InsuranceClaimDTO getClaimById(UUID id) {
        InsuranceClaim claim = claimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InsuranceClaim", "id", id));
        return billingMapper.toDTO(claim);
    }

    @Transactional
    public InsuranceClaimDTO updateClaimStatus(UUID id, UpdateClaimStatusRequest request) {
        InsuranceClaim claim = claimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InsuranceClaim", "id", id));
        claim.setStatus(request.getStatus());
        if (request.getApprovedAmount() != null) claim.setApprovedAmount(request.getApprovedAmount());
        if (request.getRejectionReason() != null) claim.setRejectionReason(request.getRejectionReason());
        if (request.getNotes() != null) claim.setNotes(request.getNotes());
        return billingMapper.toDTO(claimRepository.save(claim));
    }
}
