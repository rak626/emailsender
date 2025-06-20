package com.rakesh.emailsender.service;

import com.rakesh.emailsender.dto.EmailRequest;
import com.rakesh.emailsender.dto.PersonalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SenderService {
    // This service can be used to handle any additional logic related to sending emails
    // For now, it is empty as the EmailService handles the email sending logic.
    // You can add methods here if you need to process or log email sending requests.
    private final EmailService emailService;

    public List<String> processEmailRequest(EmailRequest request) {
        List<CompletableFuture<String>> futures = new ArrayList<>();
        PersonalDetails personalDetails = request.getPersonalDetails();

        request.getJobApplications().forEach(jobApplication -> {
            futures.add(emailService.sendEmail(personalDetails, jobApplication));
        });

        // Now wait for all to finish
        CompletableFuture<Void> all = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        all.join(); // wait for all to complete

        // Collect results
        return futures.stream()
                .map(CompletableFuture::join) // still non-blocking, just waits if needed
                .collect(Collectors.toList());
    }

}
