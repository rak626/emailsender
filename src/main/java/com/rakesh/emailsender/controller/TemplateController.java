package com.rakesh.emailsender.controller;

import com.rakesh.emailsender.dto.CreateTemplateRequest;
import com.rakesh.emailsender.entity.EmailType;
import com.rakesh.emailsender.entity.RecipientType;
import com.rakesh.emailsender.entity.Template;
import com.rakesh.emailsender.entity.TemplateComponent;
import com.rakesh.emailsender.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    @GetMapping
    public ResponseEntity<Template> getTemplate(
            @RequestParam RecipientType recipientType,
            @RequestParam EmailType emailType,
            @RequestParam String version) {
        return ResponseEntity.ok(templateService.getTemplate(recipientType, emailType, version));
    }

    @PostMapping("/components")
    public ResponseEntity<TemplateComponent> createComponent(@RequestBody TemplateComponent component) {
        TemplateComponent saved = templateService.saveComponent(component);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping
    public ResponseEntity<Template> createTemplate(@RequestBody CreateTemplateRequest request) {
        Template saved = templateService.createTemplate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

}
