package com.rakesh.emailsender.util;

import com.rakesh.emailsender.dto.CreateTemplateRequest;
import com.rakesh.emailsender.entity.EmailType;
import com.rakesh.emailsender.entity.RecipientType;
import com.rakesh.emailsender.service.TemplateService;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.RequiredArgsConstructor;

@ChangeUnit(id = "init-template", order = "002")
@RequiredArgsConstructor
public class TemplateChangeLog {
    private final TemplateService service;

    @Execution
    public void seedTemplateData() {
        CreateTemplateRequest req = new CreateTemplateRequest(
                RecipientType.RECRUITER,
                EmailType.COLD_EMAIL,
                "v1",
                "Application for Software Engineer Position - 3+ Years Experience",
                getEmailBody(),
                "1",
                "2",
                "3",
                null);
        service.createTemplate(req);
        CreateTemplateRequest req1 = new CreateTemplateRequest(
                RecipientType.RECRUITER,
                EmailType.COLD_EMAIL,
                "v2",
                "Application for Backend Developer Position - 3+ YOE",
                getEmailBody2(),
                null,
                null,
                null,
                null
        );
        service.createTemplate(req1);
    }

    @RollbackExecution
    public void rollBackSeedData() {
        System.out.println("rolled back template Data");
    }

    private String getEmailBody() {
        return """
                <div>
                  <p style="margin: 0 0 15px;">Hi [Recruiter Name],</p>
                  <p style="margin: 0 0 15px;"> I hope you're doing well. I recently came across an opportunity at <strong>[Company Name]</strong> for the role of <strong>[Job Title]</strong>, and I’m writing to express my interest in the position. </p>
                  <p style="margin: 0 0 15px;"> With over <strong>[X years]</strong> of experience in <strong>[Your Domain - e.g., Java backend development]</strong>, I’ve worked on scalable systems and contributed to end-to-end product development. I'm particularly skilled in technologies like <strong>[Tech Stack]</strong> and passionate about building reliable, secure systems. </p>
                  <p style="margin: 0 0 15px;"> I would love the opportunity to connect and discuss how I can contribute to your team. I’ve attached my resume for your reference. </p>
                  <p style="margin: 0 0 15px;"> Looking forward to hearing from you! </p>
                  <p style="margin: 0 0 5px;">Best regards,</p>
                  <p style="margin: 0 0 5px;">
                    <strong>[Your Full Name]</strong>
                  </p>
                  <p style="margin: 0 0 5px;">
                    <a href="mailto:[YourEmail@example.com]" style="color: #007bff;">[YourEmail@example.com]</a> | +91-[Your Phone]
                  </p>
                  <p style="margin: 0 0 5px;">
                    <a href="https://www.linkedin.com/in/[LinkedInID]" target="_blank" style="color: #007bff;">LinkedIn</a> | <a href="https://github.com/[GitHubID]" target="_blank" style="color: #007bff;">GitHub</a>
                  </p>
                </div>""";
    }

    private String getEmailBody2() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                  <meta charset="UTF-8">
                  <title>Application for Backend Developer Role</title>
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <style>
                    body {
                      margin: 5px;
                      font-family: Arial, sans-serif;
                      font-size: 14px;
                      color: #000000;
                      line-height: 1.5;
                      background-color: #ffffff;
                    }
                    a {
                      color: #007b99;
                      text-decoration: none;
                    }
                    b {
                      color: #24221d;
                    }
                  </style>
                </head>
                <body>
                
                <p>Hi $${recruiterName},</p>
                
                <p>I hope you're doing well.</p>
                
                <p>
                I came across an opportunity at <b>$${companyName}</b> for a <b> $${jobRole}</b> role $${jobLink} and wanted to reach out to express my interest.
                </p>
                
                <p>
                With over <b>3.5 years</b> of experience in backend development, primarily in the payments domain,\s
                I've contributed to building scalable, high-performance systems in both product-based and service environments.
                </p>
                
                <p>
                At <b>RS Software</b>, I worked on core payment flows like Auth, Sale, Refund, Installment, and Recurring payments,\s
                integrating with processors like <b>TSYS</b> and <b>WorldPay</b> as well as card brands such as\s
                <b>VISA</b>, <b>Mastercard</b>, and <b>AMEX</b> etc. I contributed to distributed microservices using\s
                <b>Spring Boot</b>, <b>Kafka</b>, and <b>Redis</b>, and also implemented event-driven notifications\s
                and centralized job scheduling using a <b>Quartz</b>-based mechanism.
                </p>
                
                <p>
                I'm confident that my expertise in backend engineering, system design, and real-time financial processing can bring value to your team.
                I've attached my resume for your reference and would be glad to discuss how I can contribute to your organization.
                </p>
                
                <p>Thank you for your time. Looking forward to connecting with you.</p>
                
                <p>
                Best regards,<br>
                <b>Rakesh Ghosh</b><br>
                <a href="mailto:rakeshacot@gmail.com">rakeshacot@gmail.com</a> | +91-7001074104<br>
                <a href="https://www.linkedin.com/in/ghoshrakesh626/" target="_blank">LinkedIn</a> |
                <a href="https://github.com/rak626" target="_blank">GitHub</a>
                </p>
                
                </body>
                </html>""";
    }

}
