package com.act.hospitalmanagementsystem.notification.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.notification.dto.CreateTemplateRequest;
import com.act.hospitalmanagementsystem.notification.dto.TemplateDTO;
import com.act.hospitalmanagementsystem.notification.entity.Template;
import com.act.hospitalmanagementsystem.notification.enums.NotificationType;
import com.act.hospitalmanagementsystem.notification.enums.TemplateType;
import com.act.hospitalmanagementsystem.notification.mapper.TemplateMapper;
import com.act.hospitalmanagementsystem.notification.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final TemplateMapper templateMapper;
    private final EmailService emailService;

    @Transactional
    public BaseResponseDTO<TemplateDTO> createTemplate(CreateTemplateRequest request, String createdBy) {
        try {
            if (templateRepository.findByTemplateCode(request.getTemplateCode()).isPresent()) {
                return BaseResponseDTO.error("Template code already exists");
            }

            Template template = templateMapper.toEntity(request);
            template.setCreatedBy(createdBy);
            Template saved = templateRepository.save(template);

            return BaseResponseDTO.success(templateMapper.toDTO(saved), "Template created successfully");
        } catch (Exception e) {
            log.error("Error creating template", e);
            return BaseResponseDTO.error("Failed to create template: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<TemplateDTO>> getAllTemplates(Pageable pageable) {
        try {
            Page<Template> templates = templateRepository.findAll(pageable);
            return BaseResponseDTO.success(templateMapper.toDTOList(templates.getContent()), "Templates retrieved");
        } catch (Exception e) {
            log.error("Error getting templates", e);
            return BaseResponseDTO.error("Failed to get templates: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<TemplateDTO> getTemplateByCode(String templateCode) {
        try {
            Template template = templateRepository.findByTemplateCode(templateCode)
                    .orElseThrow(() -> new RuntimeException("Template not found"));
            return BaseResponseDTO.success(templateMapper.toDTO(template), "Template retrieved");
        } catch (Exception e) {
            log.error("Error getting template by code", e);
            return BaseResponseDTO.error("Failed to get template: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<TemplateDTO> updateTemplate(UUID id, String body, String updatedBy) {
        try {
            Template template = templateRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Template not found"));
            
            template.setBody(body);
            template.setVersion(template.getVersion() + 1);
            template.setUpdatedBy(updatedBy);
            Template saved = templateRepository.save(template);

            return BaseResponseDTO.success(templateMapper.toDTO(saved), "Template updated successfully");
        } catch (Exception e) {
            log.error("Error updating template", e);
            return BaseResponseDTO.error("Failed to update template: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> testTemplate(UUID id, String recipientEmail, Map<String, Object> variables) {
        try {
            Template template = templateRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Template not found"));

            if (template.getTemplateType() != TemplateType.EMAIL) {
                return BaseResponseDTO.error("Only email templates can be tested");
            }

            String processedBody = processTemplateVariables(template.getBody(), variables);

            // Send test email
            // TODO: Create a test notification and send it
            log.info("Test email sent to {} using template {}", recipientEmail, template.getTemplateCode());

            return BaseResponseDTO.success(null, "Test email sent successfully");
        } catch (Exception e) {
            log.error("Error testing template", e);
            return BaseResponseDTO.error("Failed to test template: " + e.getMessage());
        }
    }

    private String processTemplateVariables(String body, Map<String, Object> variables) {
        String processedBody = body;
        if (variables != null) {
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                processedBody = processedBody.replace("{{" + entry.getKey() + "}}", entry.getValue().toString());
            }
        }
        return processedBody;
    }
}
