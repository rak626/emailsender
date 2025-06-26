package com.rakesh.emailsender.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakesh.emailsender.dto.EmailRequest;
import com.rakesh.emailsender.dto.JobApplication;
import com.rakesh.emailsender.dto.PersonalDetails;
import com.rakesh.emailsender.entity.Application;
import com.rakesh.emailsender.repo.ApplicationRepo;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ObjectMapper objectMapper;
    private final ApplicationRepo appRepo;

    public void createApplicationEntry(PersonalDetails personalDetails, JobApplication jobApplication, boolean isSent) {
        Application application = new Application();
        application.setJobRole(jobApplication.getJobDesc().getRole());
        application.setCompany(jobApplication.getJobDesc().getCompany());
        application.setJobLink(jobApplication.getJobDesc().getJobLink());
        application.setLocation(jobApplication.getJobDesc().getLocation());
        application.setRecipientType(jobApplication.getRecipientType());
        application.setEmailType(jobApplication.getEmailType());
        application.setVersion(jobApplication.getVersion());
        application.setRecipientName(personalDetails.getName());
        application.setRecipientEmail(personalDetails.getEmail());
        application.setRecipientDesignation(jobApplication.getRecipientDesc().getPosition());
        application.setSent(isSent);
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setPersonalDetails(personalDetails);
        emailRequest.setJobApplications(List.of(jobApplication));
        try {
            application.setRequestObj(Document.parse(objectMapper.writeValueAsString(emailRequest)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        appRepo.save(application);
    }
}
