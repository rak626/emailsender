package com.rakesh.emailsender.dto;

import com.rakesh.emailsender.entity.EmailType;
import com.rakesh.emailsender.entity.RecipientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTemplateRequest {
    private RecipientType recipientType;
    private EmailType emailType;
    private String version;
    private String subject;
    private String emailBody;

    private String mainContentId;
    private String styleId;
    private String headerId;
    private String footerId;
}

