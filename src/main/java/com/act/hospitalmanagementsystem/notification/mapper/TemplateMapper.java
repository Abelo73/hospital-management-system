package com.act.hospitalmanagementsystem.notification.mapper;

import com.act.hospitalmanagementsystem.notification.dto.CreateTemplateRequest;
import com.act.hospitalmanagementsystem.notification.dto.TemplateDTO;
import com.act.hospitalmanagementsystem.notification.entity.Template;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TemplateMapper {

    private final ObjectMapper objectMapper;

    public TemplateMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public TemplateDTO toDTO(Template template) {
        if (template == null) {
            return null;
        }

        TemplateDTO dto = new TemplateDTO();
        dto.setId(template.getId());
        dto.setTemplateCode(template.getTemplateCode());
        dto.setTemplateName(template.getTemplateName());
        dto.setTemplateType(template.getTemplateType());
        dto.setNotificationType(template.getNotificationType());
        dto.setSubject(template.getSubject());
        dto.setBody(template.getBody());
        dto.setVariables(template.getVariables());
        dto.setLanguage(template.getLanguage());
        dto.setIsActive(template.getIsActive());
        dto.setVersion(template.getVersion());
        dto.setDescription(template.getDescription());
        dto.setCreatedAt(template.getCreatedAt());
        dto.setCreatedBy(template.getCreatedBy());
        return dto;
    }

    public Template toEntity(CreateTemplateRequest request) {
        if (request == null) {
            return null;
        }

        Template template = new Template();
        template.setTemplateCode(request.getTemplateCode());
        template.setTemplateName(request.getTemplateName());
        template.setTemplateType(request.getTemplateType());
        template.setNotificationType(request.getNotificationType());
        template.setSubject(request.getSubject());
        template.setBody(request.getBody());
        template.setVariables(variablesToJson(request.getVariables()));
        template.setLanguage(request.getLanguage());
        template.setIsActive(request.getIsActive());
        template.setVersion(1);
        template.setDescription(request.getDescription());
        return template;
    }

    public List<TemplateDTO> toDTOList(List<Template> templates) {
        return templates.stream()
                .map(this::toDTO)
                .toList();
    }

    public String variablesToJson(List<String> variables) {
        try {
            return objectMapper.writeValueAsString(variables);
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }
}
