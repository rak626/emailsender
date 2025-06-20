package com.rakesh.emailsender.dto;

import com.rakesh.emailsender.entity.EmailType;
import com.rakesh.emailsender.entity.RecipientType;
import lombok.Data;

@Data
public class JobApplication {
    private RecipientType recipientType;
    private EmailType emailType;
    private String subject;
    private String version;
    private String body;
    private JobDescription jobDesc;
    private RecipientDescription recipientDesc;
}
