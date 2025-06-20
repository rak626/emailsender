package com.rakesh.emailsender.service;

import com.rakesh.emailsender.dto.JobApplication;
import com.rakesh.emailsender.dto.PersonalDetails;
import com.rakesh.emailsender.entity.Template;
import com.rakesh.emailsender.entity.TemplateComponent;
import com.rakesh.emailsender.util.AppUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private static final String RESUME_PATH = "resume/Rakesh_Ghosh_Resume.pdf";
    private static final String RESUME_NAME = "Rakesh_Ghosh_Resume.pdf";
    private static final String DEFAULT_FROM_EMAIL = "rakeshacot@gmail.com";

    private final JavaMailSender mailSender;
    private final TemplateService templateService;

    @Async
    public CompletableFuture<String> sendEmail(PersonalDetails personalDetails, JobApplication jobApplication) {
        log.info("Preparing to send email to: {}, at {}",
                jobApplication.getRecipientDesc().getEmails(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            buildMimeMessage(helper, personalDetails, jobApplication);
            mailSender.send(message);
            log.info("Email sent successfully to: {}, at {}",
                    jobApplication.getRecipientDesc().getEmails(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            return CompletableFuture.completedFuture("Email sent successfully! to: " + jobApplication.getRecipientDesc().getEmails());
        } catch (Exception ex) {
            log.error("Failed to send email: {}", ex.getMessage(), ex);
            return CompletableFuture.completedFuture("Failed to send email: " + ex.getMessage());
        }
    }

    private void buildMimeMessage(MimeMessageHelper helper, PersonalDetails personalDetails, JobApplication jobApplication) throws MessagingException {
        helper.setFrom(DEFAULT_FROM_EMAIL);
        helper.setTo(jobApplication.getRecipientDesc().getEmails().toArray(new String[0]));
        helper.setSubject(jobApplication.getSubject());
        helper.setText(prepareFullHtml(jobApplication), true);
        attachResume(helper);
    }

    private void attachResume(MimeMessageHelper helper) {
        try {
            ClassPathResource resource = new ClassPathResource(RESUME_PATH);
            helper.addAttachment(RESUME_NAME, resource);
        } catch (Exception e) {
            log.warn("Failed to attach resume: {}", e.getMessage(), e);
        }
    }

    private String prepareFullHtml(JobApplication jobApplication) {
        Template template = templateService.getTemplate(
                jobApplication.getRecipientType(),
                jobApplication.getEmailType(),
                jobApplication.getVersion()
        );

        String mainContent = getSafeContent(template.getMainContent(), "");

        String header = getSafeContent(template.getHeader(), "");
        String footer = getSafeContent(template.getFooter(), "");
        String style = getSafeContent(template.getStyle(), "");

        String bodyContent = StringUtils.isBlank(jobApplication.getBody())
                ? getSafeContent(template::getBody, "")
                : wrapInHtml(jobApplication.getBody());

        String emailBody = StringUtils.isBlank(mainContent) ? bodyContent : mainContent
                .replace("###{{header}}###", header)
                .replace("###{{footer}}###", footer)
                .replace("###{{style}}###", style)
                .replace("###{{body}}###", bodyContent);
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("recruiterName", AppUtil.getFirstName(jobApplication.getRecipientDesc().getName()));
        valuesMap.put("companyName", jobApplication.getJobDesc().getCompany());
        valuesMap.put("jobLink", getJobLink(jobApplication));
        valuesMap.put("jobRole", jobApplication.getJobDesc().getRole());
        StringSubstitutor substitutor = new StringSubstitutor(valuesMap, "$${", "}");
        return substitutor.replace(emailBody);
    }

    private String getJobLink(JobApplication jobApplication) {
        String link = jobApplication.getJobDesc().getJobLink();
        return StringUtils.isBlank(link) ? "" : String.format("(<a href=\"%s\">Job-Link</a>)", link);
    }


    private String getSafeContent(TemplateComponent component, String defaultValue) {
        return (component != null && component.getContent() != null)
                ? component.getContent()
                : defaultValue;
    }

    private String getSafeContent(Supplier<String> supplier, String defaultValue) {
        try {
            String content = supplier.get();
            return content != null ? content : defaultValue;
        } catch (Exception e) {
            log.warn("Error retrieving content: {}", e.getMessage());
            return defaultValue;
        }
    }

    private String wrapInHtml(String body) {
        return String.format("""
                <html>
                  <body>
                    %s
                  </body>
                </html>
                """, body);
    }
}
