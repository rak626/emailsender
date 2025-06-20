package com.rakesh.emailsender.controller;

import com.rakesh.emailsender.dto.EmailRequest;
import com.rakesh.emailsender.service.EmailService;
import com.rakesh.emailsender.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/send")
@RequiredArgsConstructor
public class SenderController {
    private final SenderService service;


    @PostMapping
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request) {
        return ResponseEntity.ok(service.processEmailRequest(request));
    }

}
