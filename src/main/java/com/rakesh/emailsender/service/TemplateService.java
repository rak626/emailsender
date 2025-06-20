package com.rakesh.emailsender.service;

import com.rakesh.emailsender.dto.CreateTemplateRequest;
import com.rakesh.emailsender.entity.EmailType;
import com.rakesh.emailsender.entity.RecipientType;
import com.rakesh.emailsender.entity.Template;
import com.rakesh.emailsender.entity.TemplateComponent;
import com.rakesh.emailsender.repo.TemplateComponentRepo;
import com.rakesh.emailsender.repo.TemplateRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepo templateRepo;
    private final TemplateComponentRepo componentRepo;

    public Template getTemplate(RecipientType recipientType, EmailType emailType, String version) {
        Template template = templateRepo.findByRecipientTypeAndEmailTypeAndVersion(recipientType, emailType, version)
                .orElseThrow(() -> new RuntimeException("Template not found"));

        // Populate referenced components
        template.setMainContent(loadComponent(template.getMainContentId()));
        template.setHeader(loadComponent(template.getHeaderId()));
        template.setFooter(loadComponent(template.getFooterId()));
        template.setStyle(loadComponent(template.getStyleId()));

        return template;
    }

    private TemplateComponent loadComponent(String id) {
        return id != null ? componentRepo.findById(id).orElse(null) : null;
    }

    @Transactional
    public Template createTemplate(CreateTemplateRequest req) {
        String id = req.getRecipientType() + "-" + req.getEmailType() + "-" + req.getVersion();

        Template template = new Template();
        template.setId(id);
        template.setRecipientType(req.getRecipientType());
        template.setEmailType(req.getEmailType());
        template.setVersion(req.getVersion());
        template.setSubject(req.getSubject());
        template.setBody(req.getEmailBody());

        template.setMainContentId(req.getMainContentId());
        template.setHeaderId(req.getHeaderId());
        template.setFooterId(req.getFooterId());
        template.setStyleId(req.getStyleId());

        return templateRepo.save(template);
    }

    @Transactional
    public TemplateComponent saveComponent(TemplateComponent component) {
        return componentRepo.save(component);
    }

}


