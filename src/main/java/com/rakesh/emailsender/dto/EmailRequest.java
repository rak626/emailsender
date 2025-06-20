package com.rakesh.emailsender.dto;

import com.rakesh.emailsender.entity.EmailType;
import com.rakesh.emailsender.entity.RecipientType;
import lombok.Data;

import java.util.List;

@Data
public class EmailRequest {
    private PersonalDetails personalDetails;
    private List<JobApplication> jobApplications;

    // for testing purpose
//    private String to;
//    private String subject;
//    private String body;
//    private RecipientType recipientType;
//    private EmailType emailType;
//    private String version;
}

