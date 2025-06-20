package com.rakesh.emailsender.util;

import com.rakesh.emailsender.entity.TemplateComponent;
import com.rakesh.emailsender.repo.TemplateComponentRepo;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;

import java.util.HashSet;
import java.util.List;

import static com.rakesh.emailsender.entity.ContentType.*;

@ChangeUnit(id = "init-template-component", order = "001", author = "admin")
public class TemplateComponentChangeLog {
    private final TemplateComponentRepo templateComponentRepo;

    public TemplateComponentChangeLog(TemplateComponentRepo templateComponentRepo) {
        this.templateComponentRepo = templateComponentRepo;
    }

    @Execution
    public void seedTemplateComponent() {
        if (templateComponentRepo.count() == 0) {
            var components = List.of(
                    new TemplateComponent("1", "MainContent", getMainContent(), MAIN_CONTENT),
                    new TemplateComponent("2", "style", getStyleContent(), STYLE),
                    new TemplateComponent("3", "header", getHeader(), HEADER),
                    new TemplateComponent("4", "footer", getFooter(), FOOTER));
            // Save all components in a single transaction
            templateComponentRepo.saveAll(new HashSet<>(components));
            System.out.println("change set applied");
        }
    }

    @RollbackExecution
    public void rollback() {
        System.out.println("🧹 Rolled back template component seeding");
    }


    private String getMainContent() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                  <meta charset="UTF-8">
                  <title>Cold Email to Recruiter</title>
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  ###{{style}}###
                </head>
                <body style="margin: 0; padding: 0; background-color: #f6f6f6; font-family: Arial, sans-serif;">
                  <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                      <td align="center" style="padding: 20px 10px;">
                        <table border="0" cellpadding="0" cellspacing="0" width="600" class="container" style="background-color: #ffffff; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.05); padding: 30px;">
                          ###{{header}}###
                          <tr>
                            <td align="left" class="content" style="font-size: 18px; line-height: 1.6; color: #333333;">
                              ###{{body}}###
                            </td>
                          </tr>
                        </table>
                        ###{{footer}}###
                      </td>
                    </tr>
                  </table>
                </body>
                </html>
                """;
    }

    private String getStyleContent() {
        return """
                <style>
                    @media only screen and (max-width: 600px) {
                      .container {
                        width: 100% !important;
                        padding: 10px !important;
                      }
                      .content {
                        font-size: 16px !important;
                      }
                      .subject-line {
                        font-size: 22px !important;
                      }
                    }
                </style>
                """;
    }

    private String getHeader() {
        return """
                <tr>
                  <td align="center" class="subject-line" style="font-size: 24px; font-weight: bold; color: #1a1a1a; padding-bottom: 20px;">
                    Application for Java Developer Role at [Company Name]
                  </td>
                </tr>
                """;
    }

    private String getFooter() {
        return """
                <tr>
                  <td align="center" style="padding-top: 20px; font-size: 14px; color: #999999;">
                    <p>Thank you for your time and consideration.</p>
                    <p>Best regards,</p>
                    <p>[Your Name]</p>
                  </td>
                </tr>
                """;
    }

}
