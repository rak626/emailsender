package com.rakesh.emailsender.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    private String id;

    // Job information
    private String company;
    private String jobTitle;
    private String jobLink;
    private String jobRole;
    private String location;

    // Template information
    private RecipientType recipientType;
    private EmailType emailType;
    private String version;

    // Recipient information
    private String recipientName;
    private String recipientEmail;
    private String recipientDesignation;

    // System information
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private boolean sent;

    private org.bson.Document requestObj; // stores incoming request JSON
}

