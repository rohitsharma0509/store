package com.app.ecom.store.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.ecom.store.dto.EmailTemplateDto;
import com.app.ecom.store.model.EmailTemplate;

public interface EmailTemplateService {

    EmailTemplateDto getEmailTemplateById(Long id);

    void addEmailTemplate(EmailTemplateDto emailTemplateDto);

    Page<EmailTemplate> getEmailTemplates(Pageable pageable);
}
